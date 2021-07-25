## 24.9 自定义Gson反序列化器

感觉就像是自己手动用JsonObject和JsonArray来解析json了，少套了一层数据。
记录一下用反序列化器(JsonDeserializer)创建Gson

    val gson = GsonBuilder()
              .registerTypeAdapter(PhotoResponse::class.java, PhotoDeserializer())
              .create()

## 24.10 分页
### 我的做法
1把原来的FlickrApi定义好的函数换成挂起函数，不用再返回Call<>了，加上查询页码

    suspend fun fetchPhotos(@Query("page") page: Int): PhotoResponse

2.改写Flickr的fetchPhotos ,给后面自己定义Paging的PagingSource来用

    suspend fun fetchPhotos(page: Int = 1): List<GalleryItem> {
           return flickrApi.fetchPhotos(page).galleryItems
        }
	
3.在ViewModel实例化Pager,loadPhoto的时候调用Pager.flow返回数据流

4.还要将原来的Adapter换成PagingDataAdapter，最后

    lifecycleScope.launch {
              photoGalleryViewModel.loadPhoto().collectLatest {
                  adapter.submitData(it)
              }
          }
		
虽然整出来了，但是还是很不理解。lifecycleScope也是协程么？
用它来调用Pager用它来返回Flow数据流?
屏幕旋转它又会去请求图片
	
## 24.11动态调整网格列

1.就通过一个默认的和定义一个屏幕600dp宽度的资源文件读取里面定义好的网格列数。
	
2.用ViewTreeObserver.OnGlobalLayoutListener记得remove掉






