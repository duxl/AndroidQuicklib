// AppAndroid 是安卓注入到浏览器的实例对象，将AppAndroid赋值给app变量，其它页面使用app就代替了AppAndroid，
// 如果安卓注入的名称不是AppAndroid，只需修改这一个地方即可
app = AppAndroid;