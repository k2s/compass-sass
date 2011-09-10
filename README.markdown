#Compass Sass plugin#
Compass, SASS and SCSS support for Grails

##Installation##
<pre><code>grails install-plugin compass-sass</code></pre>

##Pre-Requisites##
To use this plugin, you will need to have jRuby installed and on the PATH. During installation, the plugin will check for the existence of Compass, and if the gem isn't installed, it will install it.

##Usage##
After installing, you're done. During grails run-app, compass is started to watch the configured source directory (default src/stylesheets) for changes to SASS or SCSS files, which lets you compile and run on the fly. This is the only time compass is invoked automatically. The following commands are also available:

* **grails compile-css** - Compiles any SASS/SCSS files in src/stylesheets to web-app/css, both locations configurable in GrassConfig.groovy.
* **grails install-blueprint** - Installs blueprint from from the compass gem.
* **grails update-compass** - Updates compass and any of its dependent gems.

##Configuration##
###GrassConfig.groovy###
<pre><code>grass {
	sass_dir = "./src/stylesheets"
	css_dir = "./web-app/css"
	images_dir = "./web-app/images"	
	// default is true
	relative_assets = true
	// other options: nested, expanded, compact, compressed
	output_style = "compact"
	// scss or sass 
	framework_output_type = "scss"	
}</code></pre>


* **sass_dir**: Directory compass uses for *compile-css* and real-time recompilation during run-app.
* **css_dir**: Output directory for compiled CSS.
* **images_dir**: Location for images referenced in CSS.
* **relative_assets**: Whether or not compass will generate relative URLs.
* **output_style**: The output format of the CSS. If you work on a team of more than one developer, you'll want to use "compressed" if you check in your compiled CSS. Any other option adds the file-system location of the SASS/SCSS file that generated the CSS.
* **framework\_output\_type**: The output of install-blueprint, either SASS or SCSS.

##What if my team members/I don't have jRuby installed?##
If jRuby is not installed, you will receive a warning during run-app, and SASS/SCSS will not be compiled. As long as you check in compiled CSS, therefore, team members not touching front-end code wouldn't need to have jRuby installed.

##License##
Licensed under the Apache License, Version 2.0. See <a href="http://www.apache.org/licenses/LICENSE-2.0">http://www.apache.org/licenses/LICENSE-2.0</a>