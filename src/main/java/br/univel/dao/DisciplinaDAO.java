package br.univel.dao;

import java.io.BufferedReader;
import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;

import javax.ws.rs.Path;

import org.apache.http.client.ClientProtocolException;
import org.codehaus.jettison.json.JSONArray;
import org.codehaus.jettison.json.JSONException;
import org.codehaus.jettison.json.JSONObject;
import org.jboss.resteasy.client.ClientRequest;
import org.jboss.resteasy.client.ClientResponse;

import br.univel.model.Curso;
import br.univel.model.Disciplina;

@Path("/json/disciplina")
public class DisciplinaDAO {

	public List<Disciplina> getDisciplinas() {
		String out = ""; //para armazenar a string completa do JSON
		List<Disciplina> disciplinas = new ArrayList<Disciplina>();
		try {

			ClientRequest request = new ClientRequest(
					"http://cadastro3-schviders.rhcloud.com/cadastro3/rest/disciplinas");
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

			disciplinas = extracted(out);
			
		} catch (ClientProtocolException e) {

			e.printStackTrace();

		} catch (IOException e) {

			e.printStackTrace();

		} catch (Exception e) {

			e.printStackTrace();
		}
		
		return disciplinas;
	}
	
	private List<Disciplina> extracted(String output) throws JSONException {
		
		JSONArray listaDisciplinas = new JSONArray(output); //transforma a tripa de string em listaJSON
		JSONObject JSdisciplina;
		List<Disciplina> lista = new ArrayList<Disciplina>();
		 
		for (int i = 0; i < listaDisciplinas.length(); i++) {
			 
			JSdisciplina = new JSONObject(listaDisciplinas.getString(i));
			Disciplina c = new Disciplina();
			c.setNome(JSdisciplina.getString("nome")) ;
			c.setId(JSdisciplina.getInt("id"));
			c.setVersion(JSdisciplina.getInt("version"));
			lista.add(c);
		}
		return lista;
	}

	public Disciplina getDisciplina(int id) {
		
		String out = ""; //para armazenar a string completa do JSON
		Disciplina disciplina = new Disciplina();
		
		try {

			ClientRequest request = new ClientRequest(
					"http://cadastro3-schviders.rhcloud.com/cadastro3/rest/disciplinas/"+String.valueOf(id));
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

			JSONObject jsDisciplina = new JSONObject(out);
			disciplina.setId(jsDisciplina.getInt("id"));
			disciplina.setNome(jsDisciplina.getString("nome"));
			disciplina.setVersion(jsDisciplina.getInt("version"));
			return disciplina;
			
		} catch (ClientProtocolException e) {

			e.printStackTrace();

		} catch (IOException e) {

			e.printStackTrace();

		} catch (Exception e) {

			e.printStackTrace();
		}
		
		return disciplina;
	}

	public boolean remover(Disciplina disciplinaSelecionada) {
		boolean retorno = false;
		try {
			
			ClientRequest request = new ClientRequest(
					"http://cadastro3-schviders.rhcloud.com/cadastro3/rest/disciplinas/"+disciplinaSelecionada.getId());
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

	public boolean atualizar(Disciplina disciplinaSelecionada) {
		boolean retorno = false;
		try {
			
			JSONObject js = new JSONObject();
			js.put("nome", disciplinaSelecionada.getNome());
			js.put("id", disciplinaSelecionada.getId());
			js.put("version", disciplinaSelecionada.getVersion());
			
			ClientRequest request = new ClientRequest(
					"http://cadastro3-schviders.rhcloud.com/cadastro3/rest/disciplinas/"+disciplinaSelecionada.getId());
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

	public boolean cadastrar(Disciplina d) {
		boolean retorno = false;
		try {
			
			JSONObject js = new JSONObject();
			js.put("nome", d.getNome());
			
			ClientRequest request = new ClientRequest(
					"http://cadastro3-schviders.rhcloud.com/cadastro3/rest/disciplinas/");
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

}
