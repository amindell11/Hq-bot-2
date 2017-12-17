set /p name=Enter Project Name:
mkdir %name%\java\main\src
(
	echo include '%name%'
) >> settings.gradle
gradlew build