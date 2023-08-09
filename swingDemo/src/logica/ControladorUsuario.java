package logica;

import excepciones.UsuarioNoExisteException;
import excepciones.UsuarioRepetidoException;

/**
 * Controlador de usuarios.
 *
 */
public class ControladorUsuario implements IControladorUsuario {

    public ControladorUsuario() {
    }
                                  // con throws digo el metodo puede larga una exception verificada
    
    public void registrarUsuario(String n, String ap, String ci) throws UsuarioRepetidoException {
        ManejadorUsuario mu = ManejadorUsuario.getinstance();
        Usuario u = mu.obtenerUsuario(ci);  // Lo voy a buscar a la coleccion
        if (u != null)  // Si lo encontre es porque ya existe
            throw new UsuarioRepetidoException("El usuario " + ci + " ya esta registrado");

        u = new Usuario(n, ap, ci);  // Creo el objeto con el contructor de la clase Usuario
        mu.addUsuario(u);
    }

    // Devuelvo una DataUsuario para ser mostrado en la capa de presentacion
    
    public DataUsuario verInfoUsuario(String ci) throws UsuarioNoExisteException {
        ManejadorUsuario mu = ManejadorUsuario.getinstance();  // mu tiene la coleccion
        Usuario u = mu.obtenerUsuario(ci);                     // u obtiene el usuario pasado por parametro 
        if (u != null) // Si lo encontre es porque ya existe, solo traigo sus datos
            return new DataUsuario(u.getNombre(), u.getApellido(), u.getCedulaIdentidad());
        else
            throw new UsuarioNoExisteException("El usuario " + ci + " no existe");

    }
    // Get de usuarios, pero no de los objetos sino de los DataUsuarios
    public DataUsuario[] getUsuarios() throws UsuarioNoExisteException {
        ManejadorUsuario mu = ManejadorUsuario.getinstance();
        Usuario[] usrs = mu.getUsuarios();  // Usa el getUsuarios que devuelve array de objetos

        if (usrs != null) {
            DataUsuario[] du = new DataUsuario[usrs.length];
            Usuario usuario;

            // Para separar logica de presentacion, no se deben devolver los Usuario,
            // sino los DataUsuario
            for (int i = 0; i < usrs.length; i++) {
                usuario = usrs[i];
                du[i] = new DataUsuario(usuario.getNombre(), usuario.getApellido(), usuario.getCedulaIdentidad());
                // pasa el array de objetos a array de DataUsuaios
            }

            return du;
        } else
            throw new UsuarioNoExisteException("No existen usuarios registrados");

    }
}
