1. 视频标题太长位置不够完全显示的话，增加省略显示的效果。
2. agp降级了：由8.6.0->8.5.0
3. videoView组件无法播放网络视频，于是引入了ExoPlayer依赖。
4. 新版本的android项目都是使用Version Catalogs管理依赖，体现在gradle/libs.versions.toml文件中。
添加依赖时，在gradle/libs.versions.toml、build.gradle.kts(:app,是在app模块下而不是整个项目下)中添加依赖。
