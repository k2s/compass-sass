import com.stefankendall.CompassInvoker
import com.stefankendall.JavaProcessKiller
import org.codehaus.groovy.grails.commons.GrailsResourceUtils
import org.grails.plugin.resource.mapper.MapperPhase

class SassResourceMapper {
    def phase = MapperPhase.GENERATION

    static defaultExcludes = ['**/*.js', '**/*.png', '**/*.gif', '**/*.jpg', '**/*.jpeg', '**/*.gz', '**/*.zip']
    static defaultIncludes = ['**/*.scss', '**/*.sass']

    private static String SASS_FILE_EXTENSIONS = ['.scss', '.sass']

    private CompassInvoker compassInvoker

    public SassResourceMapper() {
        compassInvoker = new CompassInvoker(getConfigFile(), new JavaProcessKiller())
    }

    def map(resource, config) {
        File originalFile = resource.processedFile

        if (resource.sourceUrl && isFileSassFile(originalFile)) {
            File input = getOriginalFileSystemFile(resource.sourceUrl);
            File output = new File(generateCompiledFileFromOriginal(originalFile.absolutePath))
            compassInvoker.compileSingleFile(input, output)

            resource.processedFile = output

            resource.contentType = 'text/css'
            resource.sourceUrlExtension = 'css'
            resource.tagAttributes.rel = 'stylesheet'
            resource.actualUrl = generateCompiledFileFromOriginal(resource.originalUrl)
        }
    }

    private File getConfigFile() {
        def configFile = new File("grails-app/conf/GrassConfig.groovy")
        def defaultConfigFile = new File("grails-app/conf/DefaultGrassConfig.groovy")
        return configFile.exists() ? configFile : defaultConfigFile
    }

    private boolean isFileSassFile(File file) {
        return SASS_FILE_EXTENSIONS.any { file.name.toLowerCase().endsWith(it) }
    }

    private String generateCompiledFileFromOriginal(String original) {
        original = original.replaceAll(/(?i)\.sass/, '.css')
        original = original.replaceAll(/(?i)\.scss/, '.css')
        original
    }

    private File getOriginalFileSystemFile(String sourcePath) {
        new File(GrailsResourceUtils.WEB_APP_DIR + sourcePath);
    }
}
