class CompassSassGrailsPlugin {
    def version = "0.7"
    def grailsVersion = "1.3.7 > *"
    def dependsOn = [resources: '1.0.2 > *']
    def pluginExcludes = [
            'grails-app/views/*.gsp',
            'web-app/**/*',
            'src/web-app/**/*',
            '**/MyAppResources.groovy',
            'src/stylesheets/**/*'
    ]

    def author = "Stefan Kendall"
    def authorEmail = "stefankendall@gmail.com"
    def title = "Compass Framework - SASS/SCSS support"
    def description = '''
Compass, SASS and SCSS support for Grails. Automatically compiles .scss/.sass during run-app, and adds other framework functionality.
'''

    def documentation = "http://grails.org/plugin/compass-sass"

    def license = "APACHE"
    def developers = [
            [name:'Joel Rosenberg']
    ]
    def scm = [url: "https://github.com/stefankendall/compass-sass"]
}