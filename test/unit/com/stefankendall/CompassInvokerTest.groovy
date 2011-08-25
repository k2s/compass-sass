package com.stefankendall

class CompassInvokerTest extends GroovyTestCase {
    CompassInvoker compass

    public void setUp() {
        compass = new CompassInvoker(new File("grails-app/conf/GrassConfig.groovy"), new JavaProcessKiller())
    }

    public void test_compile() {
        def config = [
                grass:
                [
                        sass_dir: 'src/stylesheets',
                        css_dir: 'src/web-app/css',
                        images_dir: 'src/web-app/images',
                        relative_assets: true
                ]
        ]
        compass = new CompassInvoker(config, new JavaProcessKiller())

        def blueprintCssFiles = [
                new File('src/web-app/css/blueprint/ie.css'),
                new File('src/web-app/css/blueprint/print.css'),
                new File('src/web-app/css/blueprint/screen.css')
        ]

        blueprintCssFiles*.delete()

        compass.compile() {}

        boolean someFileNotCreated = blueprintCssFiles.any { !it.exists() }
        assertFalse("One of ${blueprintCssFiles} not created", someFileNotCreated)
    }

    public void test_compass_gem_is_installed() {
        def output = new ByteArrayOutputStream()

        Process p = compass.runCompassCommand(['--version'] as String[], new PrintStream(output))
        p.waitFor()

        String processOutput = output.toString()
        assertTrue("Compass gem does not seem to be runnable: $processOutput", processOutput.contains("0.11"))
    }

    public void test_killing_compass_doesnt_leak_processes() {
        def javaProcessKiller = new JavaProcessKiller()
        int javaProcessCount = javaProcessKiller.getRunningJavaProcesses().size()
        compass.watch()
        compass.killCompass()
        int newJavaProcessCount = javaProcessKiller.getRunningJavaProcesses().size()

        assertEquals("Watch command is leaking processes", javaProcessCount, newJavaProcessCount)
    }
}
