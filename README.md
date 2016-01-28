<a href="url"><img src="https://github.com/RonyBrosh/TutorialProgressBar/blob/master/Graphics/ic_launcher.png" align="left" height="72" width="72" ></a>

Tutorial Progress Bar
=================================
**Customizable animated progress bar view for use with app's wizards and tutorials**


*TutorialProgressBar class and the sample App is compatible with Android 4.0 (API level 14) and above.*

> Screenshot taken from [KIDOZ](https://play.google.com/store/apps/details?id=com.kidoz) app </br>
<a href="url"><img src="https://github.com/RonyBrosh/TutorialProgressBar/blob/master/Graphics/kidoz_demo.gif" align="left" height="480" width="270" ></a>

</br></br></br></br></br></br></br></br></br></br></br></br></br></br></br></br></br></br></br>
###Features
> Classic </br>
<a href="url"><img src="https://github.com/RonyBrosh/TutorialProgressBar/blob/master/Graphics/demo_1.gif" align="left" height="126.4" width="270" ></a>

</br></br></br></br></br></br>

> Diffrent steps length </br>
<a href="url"><img src="https://github.com/RonyBrosh/TutorialProgressBar/blob/master/Graphics/demo_2.gif" align="left" height="203.3" width="270" ></a>

</br></br></br></br></br></br></br></br></br>

> Customize front and background step colors </br>
<a href="url"><img src="https://github.com/RonyBrosh/TutorialProgressBar/blob/master/Graphics/demo_3.gif" align="left" height="172.5" width="270" ></a>

</br></br></br></br></br></br></br>

> Customize progress mask </br>
<a href="url"><img src="https://github.com/RonyBrosh/TutorialProgressBar/blob/master/Graphics/demo_4.gif" align="left" height="183.2" width="135" ></a>
<a href="url"><img src="https://github.com/RonyBrosh/TutorialProgressBar/blob/master/Graphics/demo_5.gif" align="left" height="142.3" width="135" ></a>

</br></br></br></br></br></br></br>
###Implementation
1. Add the file `TutorialProgressBar.java` to your project</br>
(A good practice will be adding this file under `custom_views` package)</br>
2. Open the file `TutorialProgressBar.java` and at the top of the file change the package name to a valid package name according to your project package hierarchy
3. Add the `TutorialProgressBar` to your xml layout
```xml
<your.own.package_name.TutorialProgressBar
	android:id="@+id/TutorialProgressBar"
	android:layout_width="100dp"
	android:layout_height="10dp">
    	
</your.own.package_name.TutorialProgressBar>
```
* You can choose your `TutorialProgressBar` width and height or you can `wrap_content` if you're using a bitmap mask 
