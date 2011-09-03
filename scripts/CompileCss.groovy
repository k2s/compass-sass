includeTargets << grailsScript("Init")
includeTargets << grailsScript("_GrailsSettings")
includeTargets << new File("${compassScssIntegrationPluginDir}/scripts/_GetCompassInvoker.groovy")

target(compileCss: "Compile sass stylesheets") {
	compass.compile() { msg ->
		event("StatusError", [msg])
		exit(-1)
	}
}

setDefaultTarget(compileCss)
