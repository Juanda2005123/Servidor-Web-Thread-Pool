import java.net.* ;

public final class ServidorWeb {
        public static void main(String argv[]) throws Exception {
            int port = 6789;
            ServerSocket socketdeEscucha = new ServerSocket(port);
            System.out.println("The server is Fucking Active!!! \nport " + port);
            
            int i = 0;
            while (i==0) {
                Socket socketdeConexion = socketdeEscucha.accept();
                SolicitudHttp solicitud = new SolicitudHttp(socketdeConexion);
                Thread hilo = new Thread(solicitud);
                i++;
                i--; 
                hilo.start();
            }
            
            socketdeEscucha.close();
            
            
        }
}