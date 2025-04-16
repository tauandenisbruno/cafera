## Getting Started

Here is our college semester project.

## Folder Structure

The workspace contains two folders by default, where:

- `src`: the folder to maintain sources
- `lib`: the folder to maintain dependencies
- `controller` the folder to fxml controller classes
- `fxml` the folder to .fxml scenebuilder files

Meanwhile, the compiled output files will be generated in the `bin` folder by default.

> If you want to customize the folder structure, open `.vscode/settings.json` and update the related settings there.
> The `launch.json` files contains the deps configuration settings.

## Dependency Management

`launch.json'

JavaFX >= v24.0
Vscodium >= v1.98.2
Extension Pack For Java (by "vscjava") >= v0.29.0
JavaFX Scene Builder >= 22.0.0

## `lib` folder:

`javafx.base.jar      jdk.jsobject.jar            libavplugin-ffmpeg-59.so    libjavafx_font_pango.so`
`javafx.controls.jar  jfx.incubator.input.jar     libavplugin-ffmpeg-60.so    libjavafx_font.so`
`javafx.fxml.jar      jfx.incubator.richtext.jar  libavplugin-ffmpeg-61.so    libjavafx_iio.so`
`javafx.graphics.jar  libavplugin-54.so           libdecora_sse.so            libjfxmedia.so`
`javafx.media.jar     libavplugin-56.so           libfxplugins.so             libjfxwebkit.so`
`javafx.properties    libavplugin-57.so           libglassgtk3.so             libprism_common.so`
`javafx.swing.jar     libavplugin-ffmpeg-56.so    libglass.so                 libprism_es2.so`
`javafx-swt.jar       libavplugin-ffmpeg-57.so    libgstreamer-lite.so        libprism_sw.so`
`javafx.web.jar       libavplugin-ffmpeg-58.so    libjavafx_font_freetype.so  sqlite-jdbc-3.49.1.0.jar`
