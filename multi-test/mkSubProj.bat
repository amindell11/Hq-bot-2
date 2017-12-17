set /p name=Enter Project Name:
mkdir %name%\src\java
(
	echo include '%name%'
) >> settings.gradle
gradlew build