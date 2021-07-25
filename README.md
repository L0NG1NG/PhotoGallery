##预加载以及缓存

###缓存

1.使用LruCache,构造器 LruCache(int maxSize)，maxSize好像都不是指的缓存大小，而就单纯的这个集合的元素大小。
（以前记得好像是定义它的缓存大小，文档里还推荐不要超过内存的1/8）。使用就跟Map一样，put存值，get取值。
不过每次put完数据后，这条数据就会被放在队列的开头。

2.每次Adapter在onBindView的时候都会让ThumbnailDowner去下载图片。
ThumbanilDowner用了ViewHolder来标识应该把下载的图片给它来显示，
而LruCache应该用图片的url当作Key,Bitmap当作Value，每次ThumbanilDowner要下载的时候就把目标图片u的url，
给到LruCache里去找Bitmp,null的话就去网络下载

<private fun handleRequest(target: T) {
		//target指代的是要显示图片的ViewHolder
        val url = requestMap[target] ?: return

        val bitmap = bitmapLruCache.get(url)
            ?: flickrFetchr.fetchPhoto(url) ?: return
		...
	}

###预加载（留着）

摊牌了，不会 