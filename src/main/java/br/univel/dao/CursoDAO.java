/**
 * 
 */
package br.univel.dao;

import java.io.BufferedReader;
import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;

import org.apache.http.client.ClientProtocolException;
import org.codehaus.jettison.json.JSONArray;
import org.codehaus.jettison.json.JSONException;
import org.codehaus.jettison.json.JSONObject;
import org.jboss.resteasy.client.ClientRequest;
import org.jboss.resteasy.client.ClientResponse;

import br.univel.model.Curso;

/**
 * @author Alexandre
 *
 */
public class CursoDAO {

	public List<Curso> getCursos(){
		String out = "";
		List<Curso> cursos = new ArrayList<Curso>();
		  try {
			
				ClientRequest request = new ClientRequest(
						"http://cadastro3-schviders.rhcloud.com/cadastro3/rest/cursos/");
				request.accept("application/json");
				ClientResponse<String> response = request.get(String.class);

				if (response.getStatus() != 200) {
					throw new RuntimeException("Failed : HTTP error code : "
						+ response.getStatus());
				}

				BufferedReader br = new BufferedReader(new InputStreamReader(
					new ByteArrayInputStream(response.getEntity().getBytes())));
				String output;
				System.out.println("Output from Server .... \n");
				while ((output = br.readLine()) != null) {
					System.out.println(output);
					out += output;
				}
				
				cursos = extracted(out);

			  } catch (ClientProtocolException e) {

				e.printStackTrace();

			  } catch (IOException e) {

				e.printStackTrace();

			  } catch (Exception e) {

				e.printStackTrace();

			  }
		  
		  
		  return cursos;
		
	}

	private List<Curso> extracted(String output) throws JSONException {
		
		JSONArray listaCursos = new JSONArray(output); //transforma a tripa de string em listaJSON	  
		JSONObject JScurso;	
		List<Curso> lista = new ArrayList<Curso>();
			
		for (int i = 0; i < listaCursos.length(); i++) {
			 
			JScurso = new JSONObject(listaCursos.getString(i));
		  
			Curso c = new Curso();
			c.setNome(JScurso.getString("nome")) ;
			c.setId(JScurso.getInt("id"));
			c.setVersion(JScurso.getInt("version"));
			
			lista.add(c);
		}
		return lista;
	}

	public Curso getCurso(int id){
		
		String out = "";
		Curso curso = new Curso();
		  try {
			
				ClientRequest request = new ClientRequest(
						"http://cadastro3-schviders.rhcloud.com/cadastro3/rest/cursos/"+String.valueOf(id));
				request.accept("application/json");
				ClientResponse<String> response = request.get(String.class);

				if (response.getStatus() != 200) {
					throw new RuntimeException("Failed : HTTP error code : "
						+ response.getStatus());
				}

				BufferedReader br = new BufferedReader(new InputStreamReader(
					new ByteArrayInputStream(response.getEntity().getBytes())));
				String output;
				System.out.println("Output from Server .... \n");
				while ((output = br.readLine()) != null) {
					System.out.println(output);
					out += output;
				}
				
				JSONObject jsCurso = new JSONObject(out);
				curso.setId(jsCurso.getInt("id"));
				curso.setNome(jsCurso.getString("nome"));
				curso.setVersion(jsCurso.getInt("version"));
				return curso;

			  } catch (ClientProtocolException e) {
				e.printStackTrace();
			  } catch (IOException e) {
				e.printStackTrace();
			  } catch (Exception e) {
				e.printStackTrace();
			  }
		  return curso;
	}

	public boolean cadastrar(Curso c) {
		boolean retorno = false;
		try {
			
			JSONObject js = new JSONObject();
			js.put("nome", c.getNome());
			
			ClientRequest request = new ClientRequest(
					"http://cadastro3-schviders.rhcloud.com/cadastro3/rest/cursos/");
			request.accept("application/json");
			
			request.body("application/json",js.toString());
			
			ClientResponse<String> response = request.post(String.class);
			if (response.getStatus() != 201) {
				retorno = false;
				throw new RuntimeException("Failed : HTTP error code : "
					+ response.getStatus());
			}else{
				retorno = true;
			}

		  } catch (ClientProtocolException e) {
			e.printStackTrace();
		  } catch (IOException e) {
			e.printStackTrace();
		  } catch (Exception e) {
			e.printStackTrace();
		  }
		return retorno;
	}

	public boolean atualizar(Curso cursoSelecionado) {
		boolean retorno = false;
		try {
			
			JSONObject js = new JSONObject();
			js.put("nome", cursoSelecionado.getNome());
			js.put("id", cursoSelecionado.getId());
			js.put("version", cursoSelecionado.getVersion());
			
			ClientRequest request = new ClientRequest(
					"http://cadastro3-schviders.rhcloud.com/cadastro3/rest/cursos/"+cursoSelecionado.getId());
			request.accept("application/json");
			
			request.body("application/json",js.toString());
			
			ClientResponse<String> response = request.put(String.class);
			if (response.getStatus() != 204) {
				retorno = false;
				throw new RuntimeException("Failed : HTTP error code : "
					+ response.getStatus());
			}else{
				retorno = true;
			}

		  } catch (ClientProtocolException e) {
			e.printStackTrace();
		  } catch (IOException e) {
			e.printStackTrace();
		  } catch (Exception e) {
			e.printStackTrace();
		  }
		return retorno;
	}
	
	public boolean remover(Curso cursoSelecionado) {
		boolean retorno = false;
		try {
			
			ClientRequest request = new ClientRequest(
					"http://cadastro3-schviders.rhcloud.com/cadastro3/rest/cursos/"+cursoSelecionado.getId());
			request.accept("application/json");
			
			ClientResponse<String> response = request.delete(String.class);
			if (response.getStatus() != 204) {
				retorno = false;
				throw new RuntimeException("Failed : HTTP error code : "
					+ response.getStatus());
			}else{
				retorno = true;
			}

		  } catch (ClientProtocolException e) {
			e.printStackTrace();
		  } catch (IOException e) {
			e.printStackTrace();
		  } catch (Exception e) {
			e.printStackTrace();
		  }
		return retorno;
	}
}
