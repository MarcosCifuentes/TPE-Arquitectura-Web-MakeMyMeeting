package login;

import javax.ws.rs.Consumes;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import entities.User;
import services.DAOUser;

@Path("/autentication")
public class AutenticationService {

	@POST
	@Consumes(MediaType.APPLICATION_JSON)
	@Produces(MediaType.APPLICATION_JSON)
	public Response autenticarUser(Credential credentials) {
		String username = credentials.getusername();
		String password = credentials.getPassword();

		try {
			usuarioValido(username, password);

			String token = emitirToken(username);
			return Response.ok(token).build();
		}


		catch(Exception e){
			return Response.status(Response.Status.UNAUTHORIZED).build();

		}

	}

	private void usuarioValido(String username, String password) {

		User resultado = DAOUser.getInstance().login(username,password);

		if (resultado==null)
			throw new RuntimeException();

	}

	private String emitirToken(String username){
		String token = TokenHelper.generarToken(username);
		TokenHelper.setToken(token, username);
		return token;
	}	
}
