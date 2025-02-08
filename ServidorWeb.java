import java.io.IOException;
import java.net.* ;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.SynchronousQueue;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;

public final class ServidorWeb {
        public static void main(String argv[]) throws Exception {
            int port = 6789;
            ServerSocket socketdeEscucha = new ServerSocket(port);
            // Creating a thread pool
            int poolSize = Runtime.getRuntime().availableProcessors();
            poolSize = 1;
            ExecutorService pool = new ThreadPoolExecutor(
                poolSize,
                100, 
                60L, TimeUnit.SECONDS,
                new SynchronousQueue<>(),
                new ThreadPoolExecutor.CallerRunsPolicy()
            );
            
            System.out.println("The server is fucking Active!!! \nport " + port);

            Runtime.getRuntime().addShutdownHook(new Thread() {
                public void run() {
                    try {
                        System.out.println("\nApagando servidor...");
                        socketdeEscucha.close();
                        pool.shutdown();
                    } catch (IOException e) {
                        System.err.println("Error en apagado: " + e.getMessage());
                    }
                }
            });
            
            try {
                while (true) { 
                    Socket socketdeConexion = socketdeEscucha.accept();
                    //socketdeConexion.setSoTimeout(5000);
                    SolicitudHttp solicitud = new SolicitudHttp(socketdeConexion);
                    pool.execute(solicitud); 
                }
            } catch (SocketException e) {
                System.out.println("Socket cerrado. Servidor detenido.");
            } finally {
                if (!pool.isShutdown()) {
                    pool.shutdown();
                }
            }
            
            
        }
}