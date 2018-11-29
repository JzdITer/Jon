package com.jzd.android.jon.core.module.image;

import android.annotation.SuppressLint;
import android.graphics.Bitmap;
import android.os.Handler;
import android.os.Looper;
import android.os.Message;
import android.util.LruCache;
import android.widget.ImageView;

import com.jzd.android.jon.utils.BitmapUtils;

import java.util.LinkedList;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Semaphore;

@SuppressLint("HandlerLeak") public class ImageLoader
{
    private static ImageLoader mImageLoader;
    /**
     * LruCache缓存对象
     */
    private LruCache<String, Bitmap> mLruCache;
    /**
     * 线程池
     */
    private ExecutorService mThreadPool;
    private static final int DEFAULT_THREAD_COUNT = 5;
    /**
     * 任务队列
     */
    private LinkedList<Runnable> mTaskQueue;
    /**
     * 后台轮询线程
     */
    private Thread mBackThread;
    private Handler mBackThreadHandler;
    private LoadType mType = LoadType.LIFO;
    /**
     * 信号量，mBackThreadHandler初始化完成后释放掉，确保mBackThreadHandler！=null
     */
    private Semaphore semaphoreBackThreadHandler = new Semaphore(0);
    /**
     * 信号量，控制线程数
     */
    private Semaphore semaphoreThreadPool;

    private Handler mUIHandler = new Handler()
    {
        public void handleMessage(Message msg)
        {
            ImageBean imageBean = (ImageBean) msg.obj;
            String path = imageBean.path;
            ImageView imageView = imageBean.imageView;
            Bitmap bitmap = imageBean.bitmap;
            if(imageView.getTag()
                    .toString()
                    .equals(path))
            {
                imageView.setImageBitmap(bitmap);
            }
        }

        ;
    };

    /**
     * 加载策略
     * fifo:先进先出 lifo:后进先出
     *
     * @author Jzd
     */
    public enum LoadType
    {
        /**
         * 先进先出
         */
        FIFO, /**
     * 后进先出
     */
    LIFO;
    }

    private ImageLoader(int threadCount, LoadType type)
    {
        init(threadCount, type);
    }

    private void init(int threadCount, LoadType type)
    {
        // lru缓存对象
        int maxSize = (int) Runtime.getRuntime()
                .maxMemory();
        int size = maxSize / 8;
        mLruCache = new LruCache<String, Bitmap>(size)
        {
            @Override
            protected int sizeOf(String key, Bitmap value)
            {
                return value.getRowBytes() * value.getHeight();
            }
        };

        // 后台轮询线程
        mBackThread = new Thread()
        {
            @SuppressLint("HandlerLeak")
            @Override
            public void run()
            {
                Looper.prepare();
                mBackThreadHandler = new Handler()
                {
                    @Override
                    public void handleMessage(Message msg)
                    {
                        try
                        {
                            // 拿到许可的线程才可以执行，控制线程的数量
                            semaphoreThreadPool.acquire();
                            mThreadPool.execute(getTask());
                            //	Log.i("aaaa", "mBackThreadHandler被创建");
                        } catch(InterruptedException e)
                        {
                            e.printStackTrace();
                        }
                    }
                };
                semaphoreBackThreadHandler.release();
                Looper.loop();
            }
        };
        mBackThread.start();
        mThreadPool = Executors.newFixedThreadPool(threadCount);
        mTaskQueue = new LinkedList<Runnable>();
        mType = type;

        semaphoreThreadPool = new Semaphore(threadCount);
    }

    /**
     * 加载图片
     *
     * @param path      图片路径
     * @param imageView
     */
    public void displayImage(final String path, final ImageView imageView)
    {
        imageView.setTag(path);

        // 先从缓存中去图片
        Bitmap bitmap = getBitmapFromLruCache(path);
        if(bitmap != null)
        {
            sendMessageToUIThread(path, imageView, bitmap);
        }
        // 从文件中取图片
        else
        {
            addTask(new Runnable()
            {
                @Override
                public void run()
                {
                    // 加载图片
                    // 图片压缩
                    Bitmap bitmap = BitmapUtils.decodeSampledBitmap(path, imageView);
                    if(bitmap != null)
                    {
                        // 加入缓存
                        addBitmapToLruCache(path, bitmap);
                        // 通知UI线程更新图片
                        sendMessageToUIThread(path, imageView, bitmap);
                    }
                    semaphoreThreadPool.release();
                }
            });
        }
    }

    /**
     * 向UI线程发送信息更新图片
     *
     * @param path
     * @param imageView
     * @param bitmap
     */
    private void sendMessageToUIThread(String path, ImageView imageView, Bitmap bitmap)
    {
        Message msg = Message.obtain();
        ImageBean imageBean = new ImageBean();
        imageBean.bitmap = bitmap;
        imageBean.path = path;
        imageBean.imageView = imageView;
        msg.obj = imageBean;
        mUIHandler.sendMessage(msg);
    }

    protected Runnable getTask()
    {
        if(mType == LoadType.FIFO)
        {
            return mTaskQueue.removeFirst();
        } else if(mType == LoadType.LIFO)
        {
            return mTaskQueue.removeLast();
        }
        return null;
    }

    /**
     * 添加任务到任务队列中，semaphore.acquire()并发时可能造成死锁，所以要同步
     *
     * @param runnable
     */
    private synchronized void addTask(Runnable runnable)
    {
        mTaskQueue.add(runnable);
        if(mBackThreadHandler == null)
        {
            try
            {
                semaphoreBackThreadHandler.acquire();
            } catch(InterruptedException e)
            {
                e.printStackTrace();
            }
        }
        mBackThreadHandler.sendEmptyMessage(0);
    }

    public Bitmap getBitmapFromLruCache(String path)
    {
        return mLruCache.get(path);
    }

    public void addBitmapToLruCache(String path, Bitmap bitmap)
    {
        if(getBitmapFromLruCache(path) == null)
        {
            mLruCache.put(path, bitmap);
        }
    }

    class ImageBean
    {
        String path;
        Bitmap bitmap;
        ImageView imageView;
    }

    /**
     * ImageLoader构造器
     *
     * @param threadCount 线程池数量
     * @param type        加载策略
     * @return
     */
    public static ImageLoader getInstance(int threadCount, LoadType type)
    {
        if(mImageLoader == null)
        {
            synchronized(ImageLoader.class)
            {
                mImageLoader = new ImageLoader(threadCount, type);
            }
        }
        return mImageLoader;
    }

    /**
     * 默认构造器   线程池1 后进先出
     *
     * @return
     */
    public static ImageLoader getInstance()
    {
        return getInstance(DEFAULT_THREAD_COUNT, LoadType.LIFO);
    }
}
