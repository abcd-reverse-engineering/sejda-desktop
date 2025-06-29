package code.sejda.tasks.html;

import org.apache.commons.lang3.builder.EqualsBuilder;
import org.apache.commons.lang3.builder.HashCodeBuilder;
import org.apache.commons.lang3.builder.ToStringBuilder;
import org.sejda.model.PageOrientation;
import org.sejda.model.parameter.base.MultipleSourceMultipleOutputParameters;
import scala.Option;
import scala.Some;
import scala.collection.immutable.Set;
import scala.reflect.ScalaSignature;
import scala.runtime.BoxedUnit;

/* compiled from: HtmlToPdfParameters.scala */
@ScalaSignature(bytes = "\u0006\u0005\t-a\u0001B\"E\u00015C\u0001b\u0017\u0001\u0003\u0006\u0004%\t\u0001\u0018\u0005\t[\u0002\u0011\t\u0011)A\u0005;\"Aa\u000e\u0001BC\u0002\u0013\u0005q\u000e\u0003\u0005u\u0001\t\u0005\t\u0015!\u0003q\u0011!)\bA!b\u0001\n\u0003y\u0007\u0002\u0003<\u0001\u0005\u0003\u0005\u000b\u0011\u00029\t\u0011]\u0004!Q1A\u0005\u0002aD\u0001\" \u0001\u0003\u0002\u0003\u0006I!\u001f\u0005\t}\u0002\u0011)\u0019!C\u0001_\"Aq\u0010\u0001B\u0001B\u0003%\u0001\u000f\u0003\u0006\u0002\u0002\u0001\u0011)\u0019!C\u0001\u0003\u0007A!\"!\u0004\u0001\u0005\u0003\u0005\u000b\u0011BA\u0003\u0011)\ty\u0001\u0001BC\u0002\u0013\u0005\u0011\u0011\u0003\u0005\u000b\u00037\u0001!\u0011!Q\u0001\n\u0005M\u0001BCA\u000f\u0001\t\u0015\r\u0011\"\u0001\u0002\u0012!Q\u0011q\u0004\u0001\u0003\u0002\u0003\u0006I!a\u0005\t\u0015\u0005\u0005\u0002A!b\u0001\n\u0003\t\u0019\u0003\u0003\u0006\u0002&\u0001\u0011\t\u0011)A\u0005\u0003\u000fA!\"a\n\u0001\u0005\u000b\u0007I\u0011AA\t\u0011)\tI\u0003\u0001B\u0001B\u0003%\u00111\u0003\u0005\n\u0003W\u0001!Q1A\u0005\u0002=D\u0011\"!\f\u0001\u0005\u0003\u0005\u000b\u0011\u00029\t\u0013\u0005=\u0002A!b\u0001\n\u0003y\u0007\"CA\u0019\u0001\t\u0005\t\u0015!\u0003q\u0011)\t\u0019\u0004\u0001BC\u0002\u0013\u0005\u0011\u0011\u0003\u0005\u000b\u0003k\u0001!\u0011!Q\u0001\n\u0005M\u0001\"CA\u001c\u0001\t\u0015\r\u0011\"\u0001p\u0011%\tI\u0004\u0001B\u0001B\u0003%\u0001\u000fC\u0005\u0002<\u0001\u0011)\u0019!C\u0001_\"I\u0011Q\b\u0001\u0003\u0002\u0003\u0006I\u0001\u001d\u0005\n\u0003\u007f\u0001!Q1A\u0005\u0002=D\u0011\"!\u0011\u0001\u0005\u0003\u0005\u000b\u0011\u00029\t\u0015\u0005\r\u0003A!b\u0001\n\u0003\t\t\u0002\u0003\u0006\u0002F\u0001\u0011\t\u0011)A\u0005\u0003'A\u0011\"a\u0012\u0001\u0005\u000b\u0007I\u0011A8\t\u0013\u0005%\u0003A!A!\u0002\u0013\u0001\b\"CA&\u0001\t\u0015\r\u0011\"\u0001p\u0011%\ti\u0005\u0001B\u0001B\u0003%\u0001\u000fC\u0004\u0002P\u0001!\t!!\u0015\t\u000f\u0005u\u0004\u0001\"\u0011\u0002��!9\u00111\u0012\u0001\u0005B\u00055\u0005bBAH\u0001\u0011\u0005\u0013\u0011S\u0004\b\u0003'#\u0005\u0012AAK\r\u0019\u0019E\t#\u0001\u0002\u0018\"9\u0011q\n\u0017\u0005\u0002\u0005}\u0005bBAQY\u0011\u0005\u00111\u0015\u0005\b\u0003ScC\u0011AAV\u0011\u001d\ty\u000b\fC\u0001\u0003cC\u0011\"!1-#\u0003%\t!a1\t\u0013\u0005eG&%A\u0005\u0002\u0005\r\u0007\"CAnYE\u0005I\u0011AAo\u0011%\t\t\u000fLI\u0001\n\u0003\t\u0019\rC\u0005\u0002d2\n\n\u0011\"\u0001\u0002f\"I\u0011\u0011\u001e\u0017\u0012\u0002\u0013\u0005\u00111\u001e\u0005\n\u0003_d\u0013\u0013!C\u0001\u0003WD\u0011\"!=-#\u0003%\t!a=\t\u0013\u0005]H&%A\u0005\u0002\u0005-\b\"CA}YE\u0005I\u0011AAb\u0011%\tY\u0010LI\u0001\n\u0003\t\u0019\rC\u0005\u0002~2\n\n\u0011\"\u0001\u0002l\"I\u0011q \u0017\u0012\u0002\u0013\u0005\u00111\u0019\u0005\n\u0005\u0003a\u0013\u0013!C\u0001\u0003\u0007D\u0011Ba\u0001-#\u0003%\t!a1\t\u0013\t\u0015A&%A\u0005\u0002\u0005-\b\"\u0003B\u0004YE\u0005I\u0011AAb\u0011%\u0011I\u0001LI\u0001\n\u0003\t\u0019MA\nIi6dGk\u001c)eMB\u000b'/Y7fi\u0016\u00148O\u0003\u0002F\r\u0006!\u0001\u000e^7m\u0015\t9\u0005*A\u0003uCN\\7O\u0003\u0002J\u0015\u0006)1/\u001a6eC*\t1*\u0001\u0003d_\u0012,7\u0001A\n\u0003\u00019\u0003\"aT-\u000e\u0003AS!!\u0015*\u0002\t\t\f7/\u001a\u0006\u0003'R\u000b\u0011\u0002]1sC6,G/\u001a:\u000b\u0005U3\u0016!B7pI\u0016d'BA%X\u0015\u0005A\u0016aA8sO&\u0011!\f\u0015\u0002'\u001bVdG/\u001b9mKN{WO]2f\u001bVdG/\u001b9mK>+H\u000f];u!\u0006\u0014\u0018-\\3uKJ\u001c\u0018\u0001B;sYN,\u0012!\u0018\t\u0004=\u001eTgBA0f!\t\u00017-D\u0001b\u0015\t\u0011G*\u0001\u0004=e>|GO\u0010\u0006\u0002I\u0006)1oY1mC&\u0011amY\u0001\u0007!J,G-\u001a4\n\u0005!L'aA*fi*\u0011am\u0019\t\u0003=.L!\u0001\\5\u0003\rM#(/\u001b8h\u0003\u0015)(\u000f\\:!\u0003!AG/\u001c7D_\u0012,W#\u00019\u0011\u0007E\u0014(.D\u0001d\u0013\t\u00198M\u0001\u0004PaRLwN\\\u0001\nQRlGnQ8eK\u0002\n\u0001\u0002]1hKNK'0Z\u0001\na\u0006<WmU5{K\u0002\nq\u0002]1hK>\u0013\u0018.\u001a8uCRLwN\\\u000b\u0002sB\u0011!p_\u0007\u0002)&\u0011A\u0010\u0016\u0002\u0010!\u0006<Wm\u0014:jK:$\u0018\r^5p]\u0006\u0001\u0002/Y4f\u001fJLWM\u001c;bi&|g\u000eI\u0001\u0007[\u0006\u0014x-\u001b8\u0002\u000f5\f'oZ5oA\u0005ia/[3xa>\u0014HoV5ei\",\"!!\u0002\u0011\tE\u0014\u0018q\u0001\t\u0004c\u0006%\u0011bAA\u0006G\n\u0019\u0011J\u001c;\u0002\u001dYLWm\u001e9peR<\u0016\u000e\u001a;iA\u0005Y\u0001.\u001b3f\u001d>$\u0018nY3t+\t\t\u0019\u0002\u0005\u0003re\u0006U\u0001cA9\u0002\u0018%\u0019\u0011\u0011D2\u0003\u000f\t{w\u000e\\3b]\u0006a\u0001.\u001b3f\u001d>$\u0018nY3tA\u0005iQo]3Qe&tG/T3eS\u0006\fa\"^:f!JLg\u000e^'fI&\f\u0007%A\u0003eK2\f\u00170\u0006\u0002\u0002\b\u00051A-\u001a7bs\u0002\n!b]2s_2d\u0007+Y4f\u0003-\u00198M]8mYB\u000bw-\u001a\u0011\u0002\u0019\u0005,H\u000f[+tKJt\u0017-\\3\u0002\u001b\u0005,H\u000f[+tKJt\u0017-\\3!\u00031\tW\u000f\u001e5QCN\u001cxo\u001c:e\u00035\tW\u000f\u001e5QCN\u001cxo\u001c:eA\u0005)\u0002/\u0019:uS\u0006d7i\u001c8uK:$\u0018\t\u001c7po\u0016$\u0017A\u00069beRL\u0017\r\\\"p]R,g\u000e^!mY><X\r\u001a\u0011\u0002\u001f]\f\u0017\u000e\u001e$peN+G.Z2u_J\f\u0001c^1ji\u001a{'oU3mK\u000e$xN\u001d\u0011\u0002\u0011QLW.\u001a>p]\u0016\f\u0011\u0002^5nKj|g.\u001a\u0011\u0002\u001f\t\u0014xn^:fe2{7-\u0019;j_:\f\u0001C\u0019:poN,'\u000fT8dCRLwN\u001c\u0011\u0002\u0019\u0019|'oY3P]\u0016\u0004\u0016mZ3\u0002\u001b\u0019|'oY3P]\u0016\u0004\u0016mZ3!\u0003\u0011a\u0017M\\4\u0002\u000b1\fgn\u001a\u0011\u0002%!,\u0017\rZ3s\r>|G/\u001a:FqR\u0014\u0018m]\u0001\u0014Q\u0016\fG-\u001a:G_>$XM]#yiJ\f7\u000fI\u0001\u0007y%t\u0017\u000e\u001e \u0015Q\u0005M\u0013qKA-\u00037\ni&a\u0018\u0002b\u0005\r\u0014QMA4\u0003S\nY'!\u001c\u0002p\u0005E\u00141OA;\u0003o\nI(a\u001f\u0011\u0007\u0005U\u0003!D\u0001E\u0011\u0015Yv\u00051\u0001^\u0011\u001dqw\u0005%AA\u0002ADq!^\u0014\u0011\u0002\u0003\u0007\u0001\u000fC\u0004xOA\u0005\t\u0019A=\t\u000fy<\u0003\u0013!a\u0001a\"I\u0011\u0011A\u0014\u0011\u0002\u0003\u0007\u0011Q\u0001\u0005\n\u0003\u001f9\u0003\u0013!a\u0001\u0003'A\u0011\"!\b(!\u0003\u0005\r!a\u0005\t\u0013\u0005\u0005r\u0005%AA\u0002\u0005\u001d\u0001\"CA\u0014OA\u0005\t\u0019AA\n\u0011!\tYc\nI\u0001\u0002\u0004\u0001\b\u0002CA\u0018OA\u0005\t\u0019\u00019\t\u0013\u0005Mr\u0005%AA\u0002\u0005M\u0001\u0002CA\u001cOA\u0005\t\u0019\u00019\t\u0011\u0005mr\u0005%AA\u0002AD\u0001\"a\u0010(!\u0003\u0005\r\u0001\u001d\u0005\n\u0003\u0007:\u0003\u0013!a\u0001\u0003'A\u0001\"a\u0012(!\u0003\u0005\r\u0001\u001d\u0005\t\u0003\u0017:\u0003\u0013!a\u0001a\u00061Q-];bYN$B!!\u0006\u0002\u0002\"9\u00111\u0011\u0015A\u0002\u0005\u0015\u0015!B8uQ\u0016\u0014\bcA9\u0002\b&\u0019\u0011\u0011R2\u0003\u0007\u0005s\u00170\u0001\u0005iCND7i\u001c3f)\t\t9!\u0001\u0005u_N#(/\u001b8h)\u0005Q\u0017a\u0005%u[2$v\u000e\u00153g!\u0006\u0014\u0018-\\3uKJ\u001c\bcAA+YM\u0019A&!'\u0011\u0007E\fY*C\u0002\u0002\u001e\u000e\u0014a!\u00118z%\u00164GCAAK\u0003)\u0019\u0018N_3J]6\u0013wJ\u001a\u000b\u0005\u0003\u000f\t)\u000b\u0003\u0004\u0002(:\u0002\rA[\u0001\u0002g\u00061\u0001.Y:i\u001f\u001a$2A[AW\u0011\u0019\t9k\fa\u0001U\u0006\u0001\"/\u001a3bGR,G\r\u0013;nY\u000e{G-\u001a\u000b\u0005\u0003g\u000by\f\u0005\u0003\u00026\u0006uVBAA\\\u0015\u0011\t9%!/\u000b\u0005\u0005m\u0016\u0001\u00026bm\u0006L1\u0001\\A\\\u0011\u0019\t9\u000b\ra\u0001U\u0006YB\u0005\\3tg&t\u0017\u000e\u001e\u0013he\u0016\fG/\u001a:%I\u00164\u0017-\u001e7uII*\"!!2+\u0007A\f9m\u000b\u0002\u0002JB!\u00111ZAk\u001b\t\tiM\u0003\u0003\u0002P\u0006E\u0017!C;oG\",7m[3e\u0015\r\t\u0019nY\u0001\u000bC:tw\u000e^1uS>t\u0017\u0002BAl\u0003\u001b\u0014\u0011#\u001e8dQ\u0016\u001c7.\u001a3WCJL\u0017M\\2f\u0003m!C.Z:tS:LG\u000fJ4sK\u0006$XM\u001d\u0013eK\u001a\fW\u000f\u001c;%g\u0005YB\u0005\\3tg&t\u0017\u000e\u001e\u0013he\u0016\fG/\u001a:%I\u00164\u0017-\u001e7uIQ*\"!a8+\u0007e\f9-A\u000e%Y\u0016\u001c8/\u001b8ji\u0012:'/Z1uKJ$C-\u001a4bk2$H%N\u0001\u001cI1,7o]5oSR$sM]3bi\u0016\u0014H\u0005Z3gCVdG\u000f\n\u001c\u0016\u0005\u0005\u001d(\u0006BA\u0003\u0003\u000f\f1\u0004\n7fgNLg.\u001b;%OJ,\u0017\r^3sI\u0011,g-Y;mi\u0012:TCAAwU\u0011\t\u0019\"a2\u00027\u0011bWm]:j]&$He\u001a:fCR,'\u000f\n3fM\u0006,H\u000e\u001e\u00139\u0003m!C.Z:tS:LG\u000fJ4sK\u0006$XM\u001d\u0013eK\u001a\fW\u000f\u001c;%sU\u0011\u0011Q\u001f\u0016\u0005\u0003\u000f\t9-\u0001\u000f%Y\u0016\u001c8/\u001b8ji\u0012:'/Z1uKJ$C-\u001a4bk2$H%\r\u0019\u00029\u0011bWm]:j]&$He\u001a:fCR,'\u000f\n3fM\u0006,H\u000e\u001e\u00132c\u0005aB\u0005\\3tg&t\u0017\u000e\u001e\u0013he\u0016\fG/\u001a:%I\u00164\u0017-\u001e7uIE\u0012\u0014\u0001\b\u0013mKN\u001c\u0018N\\5uI\u001d\u0014X-\u0019;fe\u0012\"WMZ1vYR$\u0013gM\u0001\u001dI1,7o]5oSR$sM]3bi\u0016\u0014H\u0005Z3gCVdG\u000fJ\u00195\u0003q!C.Z:tS:LG\u000fJ4sK\u0006$XM\u001d\u0013eK\u001a\fW\u000f\u001c;%cU\nA\u0004\n7fgNLg.\u001b;%OJ,\u0017\r^3sI\u0011,g-Y;mi\u0012\nd'\u0001\u000f%Y\u0016\u001c8/\u001b8ji\u0012:'/Z1uKJ$C-\u001a4bk2$H%M\u001c\u00029\u0011bWm]:j]&$He\u001a:fCR,'\u000f\n3fM\u0006,H\u000e\u001e\u00132q\u0005aB\u0005\\3tg&t\u0017\u000e\u001e\u0013he\u0016\fG/\u001a:%I\u00164\u0017-\u001e7uIEJ\u0004")
/* loaded from: com.sejda.common-0.1-SNAPSHOT.jar:code/sejda/tasks/html/HtmlToPdfParameters.class */
public class HtmlToPdfParameters extends MultipleSourceMultipleOutputParameters {
    private final Set<String> urls;
    private final Option<String> htmlCode;
    private final Option<String> pageSize;
    private final PageOrientation pageOrientation;
    private final Option<String> margin;
    private final Option<Object> viewportWidth;
    private final Option<Object> hideNotices;
    private final Option<Object> usePrintMedia;
    private final int delay;
    private final Option<Object> scrollPage;
    private final Option<String> authUsername;
    private final Option<String> authPassword;
    private final Option<Object> partialContentAllowed;
    private final Option<String> waitForSelector;
    private final Option<String> timezone;
    private final Option<String> browserLocation;
    private final Option<Object> forceOnePage;
    private final Option<String> lang;
    private final Option<String> headerFooterExtras;

    public static String redactedHtmlCode(final String s) {
        return HtmlToPdfParameters$.MODULE$.redactedHtmlCode(s);
    }

    public static String hashOf(final String s) {
        return HtmlToPdfParameters$.MODULE$.hashOf(s);
    }

    public static int sizeInMbOf(final String s) {
        return HtmlToPdfParameters$.MODULE$.sizeInMbOf(s);
    }

    public Set<String> urls() {
        return this.urls;
    }

    public HtmlToPdfParameters(final Set<String> urls, final Option<String> htmlCode, final Option<String> pageSize, final PageOrientation pageOrientation, final Option<String> margin, final Option<Object> viewportWidth, final Option<Object> hideNotices, final Option<Object> usePrintMedia, final int delay, final Option<Object> scrollPage, final Option<String> authUsername, final Option<String> authPassword, final Option<Object> partialContentAllowed, final Option<String> waitForSelector, final Option<String> timezone, final Option<String> browserLocation, final Option<Object> forceOnePage, final Option<String> lang, final Option<String> headerFooterExtras) {
        this.urls = urls;
        this.htmlCode = htmlCode;
        this.pageSize = pageSize;
        this.pageOrientation = pageOrientation;
        this.margin = margin;
        this.viewportWidth = viewportWidth;
        this.hideNotices = hideNotices;
        this.usePrintMedia = usePrintMedia;
        this.delay = delay;
        this.scrollPage = scrollPage;
        this.authUsername = authUsername;
        this.authPassword = authPassword;
        this.partialContentAllowed = partialContentAllowed;
        this.waitForSelector = waitForSelector;
        this.timezone = timezone;
        this.browserLocation = browserLocation;
        this.forceOnePage = forceOnePage;
        this.lang = lang;
        this.headerFooterExtras = headerFooterExtras;
    }

    public Option<String> htmlCode() {
        return this.htmlCode;
    }

    public Option<String> pageSize() {
        return this.pageSize;
    }

    public PageOrientation pageOrientation() {
        return this.pageOrientation;
    }

    public Option<String> margin() {
        return this.margin;
    }

    public Option<Object> viewportWidth() {
        return this.viewportWidth;
    }

    public Option<Object> hideNotices() {
        return this.hideNotices;
    }

    public Option<Object> usePrintMedia() {
        return this.usePrintMedia;
    }

    public int delay() {
        return this.delay;
    }

    public Option<Object> scrollPage() {
        return this.scrollPage;
    }

    public Option<String> authUsername() {
        return this.authUsername;
    }

    public Option<String> authPassword() {
        return this.authPassword;
    }

    public Option<Object> partialContentAllowed() {
        return this.partialContentAllowed;
    }

    public Option<String> waitForSelector() {
        return this.waitForSelector;
    }

    public Option<String> timezone() {
        return this.timezone;
    }

    public Option<String> browserLocation() {
        return this.browserLocation;
    }

    public Option<Object> forceOnePage() {
        return this.forceOnePage;
    }

    public Option<String> lang() {
        return this.lang;
    }

    public Option<String> headerFooterExtras() {
        return this.headerFooterExtras;
    }

    @Override // org.sejda.model.parameter.base.MultipleSourceMultipleOutputParameters, org.sejda.model.parameter.base.MultipleSourceParameters, org.sejda.model.parameter.base.AbstractPdfOutputParameters, org.sejda.model.parameter.base.AbstractParameters
    public boolean equals(final Object other) {
        if (other instanceof HtmlToPdfParameters) {
            HtmlToPdfParameters htmlToPdfParameters = (HtmlToPdfParameters) other;
            return new EqualsBuilder().appendSuper(super.equals(htmlToPdfParameters)).append(urls(), htmlToPdfParameters.urls()).append(htmlCode(), htmlToPdfParameters.htmlCode()).append(pageSize(), htmlToPdfParameters.pageSize()).append(pageOrientation(), htmlToPdfParameters.pageOrientation()).append(margin(), htmlToPdfParameters.margin()).append(viewportWidth(), htmlToPdfParameters.viewportWidth()).append(hideNotices(), htmlToPdfParameters.hideNotices()).append(usePrintMedia(), htmlToPdfParameters.usePrintMedia()).append(delay(), htmlToPdfParameters.delay()).append(scrollPage(), htmlToPdfParameters.scrollPage()).append(partialContentAllowed(), htmlToPdfParameters.partialContentAllowed()).append(waitForSelector(), htmlToPdfParameters.waitForSelector()).append(timezone(), htmlToPdfParameters.timezone()).append(browserLocation(), htmlToPdfParameters.browserLocation()).append(forceOnePage(), htmlToPdfParameters.forceOnePage()).append(lang(), htmlToPdfParameters.lang()).append(headerFooterExtras(), htmlToPdfParameters.headerFooterExtras()).isEquals();
        }
        return false;
    }

    @Override // org.sejda.model.parameter.base.MultipleSourceMultipleOutputParameters, org.sejda.model.parameter.base.MultipleSourceParameters, org.sejda.model.parameter.base.AbstractPdfOutputParameters, org.sejda.model.parameter.base.AbstractParameters
    public int hashCode() {
        return new HashCodeBuilder(17, 37).appendSuper(super.hashCode()).append(urls()).append(htmlCode()).append(pageSize()).append(pageOrientation()).append(margin()).append(viewportWidth()).append(hideNotices()).append(usePrintMedia()).append(delay()).append(scrollPage()).append(partialContentAllowed()).append(waitForSelector()).append(timezone()).append(browserLocation()).append(forceOnePage()).append(lang()).append(headerFooterExtras()).toHashCode();
    }

    @Override // org.sejda.model.parameter.base.AbstractParameters
    public String toString() {
        ToStringBuilder builder = new ToStringBuilder(BoxedUnit.UNIT).append("urls", urls()).append("pageSize", pageSize()).append("pageOrientation", pageOrientation()).append("margin", margin()).append("viewportWidth", viewportWidth()).append("hideNotices", hideNotices()).append("usePrintMedia", usePrintMedia()).append("delay", delay()).append("scrollPage", scrollPage()).append("partialContentAllowed", partialContentAllowed()).append("waitForSelector", waitForSelector()).append("timezone", timezone()).append("browserLocation", browserLocation()).append("forceOnePage", forceOnePage()).append("lang", lang()).append("headerFooterExtras", headerFooterExtras());
        Some someHtmlCode = htmlCode();
        if (someHtmlCode instanceof Some) {
            String code2 = (String) someHtmlCode.value();
            builder.append("htmlCode", new StringBuilder(6).append("Some(").append(HtmlToPdfParameters$.MODULE$.redactedHtmlCode(code2)).append(")").toString());
        } else {
            builder.append("htmlCode", htmlCode());
        }
        return builder.toString();
    }
}
