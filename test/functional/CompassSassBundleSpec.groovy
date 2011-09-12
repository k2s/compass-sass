import geb.spock.GebSpec

class CompassSassBundleSpec extends GebSpec {
    final def serverUrl = 'http://localhost:8080/compass-sass'
    File compassWatchedScssFile = new File('src/stylesheets/test.scss')
    String compassWatchedScssFileText

    File resourcesWatchedScssFile = new File('web-app/sass/test.scss')
    String resourcesWatchedScssFileText

    def setup() {
        compassWatchedScssFileText = compassWatchedScssFile.text
        resourcesWatchedScssFileText = resourcesWatchedScssFile.text
    }

    def cleanup() {
        compassWatchedScssFile.withWriter { writer -> writer.write(compassWatchedScssFileText)}
        resourcesWatchedScssFile.withWriter { writer -> writer.write(resourcesWatchedScssFileText)}
    }

    def "Startup succeeds"() {
        when:
        loadTestPage()
        then:
        $('h1').text() == 'SASS Test'
    }

    def "check compass watch is compiling scss in src stylesheets"() {
        when:
        loadTestPage()
        then:
        $('h4').jquery.css('color') == 'rgb(255, 192, 203)'
    }

//    def "changes to scss files are compiled dynamically by compass --watch"() {
//        when:
//        compassWatchedScssFile.withWriter { writer ->
//            writer.writeLine('h4{color: rgb(255,0,255);}')
//        }
//        delayAfterChangeFile()
//        loadTestPage()
//        then:
//        $('h4').jquery.css('color') == 'rgb(255, 0, 255)'
//    }

    def "resources-type compilation of scss is working"() {
        when:
        loadTestPage()
        then:
        $('h1').jquery.css('color') == 'rgb(34, 34, 251)'
        $('h2').jquery.css('color') == 'rgb(132, 34, 16)'
        $('h3').jquery.css('color') == 'rgb(34, 251, 34)'
    }
//
//    def "changes to resources-managed files are recompiled dynamically"() {
//        when:
//        resourcesWatchedScssFile.withWriter { writer ->
//            writer.append('h1{color: rgb(50, 50, 50)}')
//        }
//        delayAfterChangeFile()
//        loadTestPage()
//        then:
//        $('h1').jquery.css('color') == 'rgb(50, 50, 50)'
//    }

    private def loadTestPage = {
        //The resources plugin tends to bomb randomly, so this will loop until the plugin stops bombing
        while ($('h1').jquery.css('color') == null) {
            go(serverUrl)
            Thread.sleep(1000)
        }
    }
}