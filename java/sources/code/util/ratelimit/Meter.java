package code.util.ratelimit;

import code.util.Loggable;
import org.slf4j.Logger;
import scala.reflect.ScalaSignature;

/* compiled from: RateLimiting.scala */
@ScalaSignature(bytes = "\u0006\u0005\u0005-a\u0001B\u000b\u0017\u0001uA\u0001\u0002\u000b\u0001\u0003\u0006\u0004%\t!\u000b\u0005\tk\u0001\u0011\t\u0011)A\u0005U!Aa\u0007\u0001BC\u0002\u0013\u0005\u0011\u0006\u0003\u00058\u0001\t\u0005\t\u0015!\u0003+\u0011!A\u0004A!b\u0001\n\u0003I\u0004\u0002\u0003 \u0001\u0005\u0003\u0005\u000b\u0011\u0002\u001e\t\u000b}\u0002A\u0011\u0001!\t\u000f\u0015\u0003\u0001\u0019!C\u0001\r\"9!\n\u0001a\u0001\n\u0003Y\u0005BB)\u0001A\u0003&q\tC\u0004W\u0001\u0001\u0007I\u0011A,\t\u000fm\u0003\u0001\u0019!C\u00019\"1a\f\u0001Q!\naCQ\u0001\u0019\u0001\u0005\u0002\u0005DQ\u0001\u001a\u0001\u0005\u0002\u0019CQ!\u001a\u0001\u0005\u0002\u0019Dq!\u001b\u0001\u0012\u0002\u0013\u0005!\u000eC\u0003v\u0001\u0011\u0005a\u000fC\u0003x\u0001\u0011\u0005\u0001\u0010C\u0003}\u0001\u0011\u0005SPA\u0003NKR,'O\u0003\u0002\u00181\u0005I!/\u0019;fY&l\u0017\u000e\u001e\u0006\u00033i\tA!\u001e;jY*\t1$\u0001\u0003d_\u0012,7\u0001A\n\u0004\u0001y!\u0003CA\u0010#\u001b\u0005\u0001#\"A\u0011\u0002\u000bM\u001c\u0017\r\\1\n\u0005\r\u0002#AB!osJ+g\r\u0005\u0002&M5\t\u0001$\u0003\u0002(1\tAAj\\4hC\ndW-\u0001\u0003oC6,W#\u0001\u0016\u0011\u0005-\u0012dB\u0001\u00171!\ti\u0003%D\u0001/\u0015\tyC$\u0001\u0004=e>|GOP\u0005\u0003c\u0001\na\u0001\u0015:fI\u00164\u0017BA\u001a5\u0005\u0019\u0019FO]5oO*\u0011\u0011\u0007I\u0001\u0006]\u0006lW\rI\u0001\u0007G2LWM\u001c;\u0002\u000f\rd\u0017.\u001a8uA\u0005\u00191MZ4\u0016\u0003i\u0002\"a\u000f\u001f\u000e\u0003YI!!\u0010\f\u0003\u0013I\u000bG/\u001a'j[&$\u0018\u0001B2gO\u0002\na\u0001P5oSRtD\u0003B!C\u0007\u0012\u0003\"a\u000f\u0001\t\u000b!:\u0001\u0019\u0001\u0016\t\u000bY:\u0001\u0019\u0001\u0016\t\u000ba:\u0001\u0019\u0001\u001e\u0002#I,W.Y5oS:<'+Z9vKN$8/F\u0001H!\ty\u0002*\u0003\u0002JA\t\u0019\u0011J\u001c;\u0002+I,W.Y5oS:<'+Z9vKN$8o\u0018\u0013fcR\u0011Aj\u0014\t\u0003?5K!A\u0014\u0011\u0003\tUs\u0017\u000e\u001e\u0005\b!&\t\t\u00111\u0001H\u0003\rAH%M\u0001\u0013e\u0016l\u0017-\u001b8j]\u001e\u0014V-];fgR\u001c\b\u0005\u000b\u0002\u000b'B\u0011q\u0004V\u0005\u0003+\u0002\u0012\u0001B^8mCRLG.Z\u0001\n]\u0016DHOU3tKR,\u0012\u0001\u0017\t\u0003?eK!A\u0017\u0011\u0003\t1{gnZ\u0001\u000e]\u0016DHOU3tKR|F%Z9\u0015\u00051k\u0006b\u0002)\r\u0003\u0003\u0005\r\u0001W\u0001\u000b]\u0016DHOU3tKR\u0004\u0003FA\u0007T\u0003\u0011aw.\u00193\u0015\u00071\u00137\rC\u0003F\u001d\u0001\u0007q\tC\u0003W\u001d\u0001\u0007\u0001,A\tv]RLGNU3tKR\u001cVmY8oIN\fA\u0001^5dWR\u0011qi\u001a\u0005\bQB\u0001\n\u00111\u0001Y\u0003\rqwn^\u0001\u000fi&\u001c7\u000e\n3fM\u0006,H\u000e\u001e\u00132+\u0005Y'F\u0001-mW\u0005i\u0007C\u00018t\u001b\u0005y'B\u00019r\u0003%)hn\u00195fG.,GM\u0003\u0002sA\u0005Q\u0011M\u001c8pi\u0006$\u0018n\u001c8\n\u0005Q|'!E;oG\",7m[3e-\u0006\u0014\u0018.\u00198dK\u00061QO\u001c;jG.$\u0012aR\u0001\u000bSN4\u0015N]:u%\u0016\fH#A=\u0011\u0005}Q\u0018BA>!\u0005\u001d\u0011un\u001c7fC:\f\u0001\u0002^8TiJLgn\u001a\u000b\u0002}B\u0019q0!\u0003\u000e\u0005\u0005\u0005!\u0002BA\u0002\u0003\u000b\tA\u0001\\1oO*\u0011\u0011qA\u0001\u0005U\u00064\u0018-C\u00024\u0003\u0003\u0001")
/* loaded from: com.sejda.common-0.1-SNAPSHOT.jar:code/util/ratelimit/Meter.class */
public class Meter implements Loggable {
    private final String name;
    private final String client;
    private final RateLimit cfg;
    private volatile int remainingRequests;
    private volatile long nextReset;
    private transient Logger logger;
    private volatile transient boolean bitmap$trans$0;

    /* JADX WARN: Multi-variable type inference failed */
    /* JADX WARN: Type inference failed for: r0v0 */
    /* JADX WARN: Type inference failed for: r0v1, types: [java.lang.Throwable] */
    /* JADX WARN: Type inference failed for: r0v8, types: [code.util.ratelimit.Meter] */
    private Logger logger$lzycompute() {
        ?? r0 = this;
        synchronized (r0) {
            if (!this.bitmap$trans$0) {
                this.logger = logger();
                r0 = this;
                r0.bitmap$trans$0 = true;
            }
        }
        return this.logger;
    }

    @Override // code.util.Loggable
    public Logger logger() {
        return !this.bitmap$trans$0 ? logger$lzycompute() : this.logger;
    }

    public String name() {
        return this.name;
    }

    public String client() {
        return this.client;
    }

    public RateLimit cfg() {
        return this.cfg;
    }

    public Meter(final String name, final String client, final RateLimit cfg) {
        this.name = name;
        this.client = client;
        this.cfg = cfg;
        Loggable.$init$(this);
        this.remainingRequests = cfg.maxRequests();
        this.nextReset = System.currentTimeMillis() + cfg.duration().toMillis();
    }

    public int remainingRequests() {
        return this.remainingRequests;
    }

    public void remainingRequests_$eq(final int x$1) {
        this.remainingRequests = x$1;
    }

    public long nextReset() {
        return this.nextReset;
    }

    public void nextReset_$eq(final long x$1) {
        this.nextReset = x$1;
    }

    public void load(final int remainingRequests, final long nextReset) {
        remainingRequests_$eq(remainingRequests);
        nextReset_$eq(nextReset);
    }

    public int untilResetSeconds() {
        return (int) ((nextReset() - System.currentTimeMillis()) / 1000);
    }

    public long tick$default$1() {
        return System.currentTimeMillis();
    }

    public int tick(final long now) {
        if (nextReset() < now) {
            nextReset_$eq(now + cfg().duration().toMillis());
            remainingRequests_$eq(cfg().maxRequests());
        }
        remainingRequests_$eq(remainingRequests() - 1);
        if (remainingRequests() < 0) {
            throw new MeterRateLimitReached(this);
        }
        return remainingRequests();
    }

    public int untick() {
        remainingRequests_$eq(remainingRequests() + 1);
        return remainingRequests();
    }

    public boolean isFirstReq() {
        return remainingRequests() == cfg().maxRequests();
    }

    public String toString() {
        return new StringBuilder(58).append("name: ").append(name()).append(", client:").append(client()).append(", cfg: ").append(cfg()).append(", remaining:").append(remainingRequests()).append(", nextReset: in ").append(untilResetSeconds()).append(" seconds").toString();
    }
}
