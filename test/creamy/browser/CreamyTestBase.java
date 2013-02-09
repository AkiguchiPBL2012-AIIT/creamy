package creamy.browser;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import javafx.application.Application;
import javafx.application.Platform;
import javafx.stage.Stage;
import org.junit.*;
import static org.junit.Assert.*;

/**
 *
 * @author miyabetaiji
 */
public abstract class CreamyTestBase {
    
    public CreamyTestBase() {}

    protected Browser browser;
    protected final Object lock = new Object();

    @Before
    public void setUp() {
        ExecutorService service = Executors.newFixedThreadPool(1);
        service.submit(new Runnable() {
            @Override
            public void run() {
                Application.launch(TestApplication.class);
            }
        });
        try { Thread.sleep(1000L); } catch (InterruptedException ex) {}
        Platform.runLater(new Runnable() {
            @Override
            public void run() {
                synchronized(lock) {
                    TestApplication.getInstance().getStage().setScene(browser = new Browser());
                }
            }
        });
        try { Thread.sleep(100L); } catch (InterruptedException ex) {}
        synchronized(lock) {}
    }
    
    public static class TestApplication extends Application {
        private static TestApplication instance;
        private Stage stage;

        @Override
        public void start(Stage stage) throws Exception {
            instance = this;
            this.stage = stage;
        }
        public static TestApplication getInstance() { return instance; }
        public Stage getStage() { return stage; }
    }
}
