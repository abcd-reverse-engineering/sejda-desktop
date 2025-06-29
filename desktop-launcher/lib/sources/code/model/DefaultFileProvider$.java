package code.model;

import code.util.StringHelpers$;
import code.util.encryption.TwoWayEncryptionUtils$;
import com.google.common.io.Files;
import java.io.File;
import java.nio.file.Paths;
import java.security.NoSuchAlgorithmException;
import java.util.regex.Pattern;
import org.sejda.model.encryption.NoEncryptionAtRest;
import org.sejda.model.input.FileSource;
import org.sejda.model.input.PdfFileSource;
import org.sejda.model.input.PdfSource;
import scala.Option;

/* compiled from: FileProvider.scala */
/* loaded from: com.sejda.common-0.1-SNAPSHOT.jar:code/model/DefaultFileProvider$.class */
public final class DefaultFileProvider$ implements FileProvider {
    public static final DefaultFileProvider$ MODULE$ = new DefaultFileProvider$();
    private static final String secret = "G'SRta^_l2MSH}Hl.Kf\"No.*:.XBMY";

    private DefaultFileProvider$() {
    }

    public String secret() {
        return secret;
    }

    @Override // code.model.FileProvider
    public PdfSource<File> getPdfSource(final String input) throws NoSuchAlgorithmException {
        String[] fragments = input.split(new StringBuilder(0).append(Pattern.quote("|")).append(Pattern.quote("|")).toString());
        if (fragments.length == 3) {
            String path = fragments[0];
            String encrypted = fragments[1];
            String salt = fragments[2];
            String pwd = TwoWayEncryptionUtils$.MODULE$.decrypt(encrypted, secret(), salt);
            File file = Paths.get(path, new String[0]).toFile();
            return PdfFileSource.newInstanceWithPassword(file, pwd);
        }
        File file2 = Paths.get(input, new String[0]).toFile();
        return PdfFileSource.newInstanceNoPassword(file2);
    }

    @Override // code.model.FileProvider
    public FileSource getSource(final String input) {
        return new FileSource(new File(input));
    }

    @Override // code.model.FileProvider
    public File createTmpDir() {
        return Files.createTempDir();
    }

    @Override // code.model.FileProvider
    public File createTmpFile(final String name) {
        return new File(Files.createTempDir(), name);
    }

    @Override // code.model.FileProvider
    public FileSource save(final byte[] bytes, final String name) {
        File dir = createTmpDir();
        File file = new File(dir, name);
        Files.write(bytes, file);
        return getSource(file.getAbsolutePath());
    }

    @Override // code.model.FileProvider
    public NoEncryptionAtRest encryption() {
        return NoEncryptionAtRest.INSTANCE;
    }

    @Override // code.model.FileProvider
    public String filenameOrDefault(final Option<String> filenameOpt) {
        return (String) filenameOpt.getOrElse(() -> {
            return new StringBuilder(10).append("sejda-").append(StringHelpers$.MODULE$.randomString(6)).append(".pdf").toString();
        });
    }
}
