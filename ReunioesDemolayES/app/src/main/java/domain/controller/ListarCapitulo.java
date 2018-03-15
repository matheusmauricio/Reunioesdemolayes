package domain.controller;

import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Toast;

import com.example.matheus.reunioesdemolayes.R;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.NameValuePair;
import org.apache.http.client.HttpClient;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.DefaultHttpClient;
import org.json.JSONArray;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;

import domain.model.Capitulo;

public class ListarCapitulo extends AppCompatActivity {

    private ArrayAdapter<String> adapter;
    private ListView listView;
    private int numeroCapitulo;
    private String nome;
    private String cidade;
    private String endereco;
    private String lojaPatrocinadora;
    private ArrayList<Capitulo> param = new ArrayList<Capitulo>();
    private android.os.Handler handler = new android.os.Handler();
    private IpServidor ipServidor = new IpServidor();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_listar_capitulo);

        Button buttonVoltar = (Button) findViewById(R.id.buttonVoltar);

        buttonVoltar.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v){
                finish();
            }
        });


        listView = (ListView) findViewById(R.id.listViewCapitulo);

        ListarCapitulo.Task task = new ListarCapitulo.Task();
        task.execute();

        listView.getSelectedItem();
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {

            @Override
            public void onItemClick(AdapterView<?> parent, View view,
                                    int position, long id) {

                Capitulo capitulo = new Capitulo();
                capitulo = (Capitulo) listView.getItemAtPosition(position);

                //Toast.makeText(ListarCapitulo.this, "Capítulo selecionado "+ capitulo, Toast.LENGTH_SHORT).show();

                numeroCapitulo = capitulo.getNumeroCapitulo();
                nome = capitulo.getNome();
                cidade = capitulo.getCidade();
                endereco = capitulo.getEndereco();
                lojaPatrocinadora = capitulo.getLojaPatrocinadora();

                Intent irParaTelaCapituloDetalhado = new Intent(getBaseContext(), CapituloDetalhado.class);
                irParaTelaCapituloDetalhado.putExtra("numeroCap", capitulo.getNumeroCapitulo());
                irParaTelaCapituloDetalhado.putExtra("nomeCap", capitulo.getNome());
                irParaTelaCapituloDetalhado.putExtra("enderecoCap", capitulo.getEndereco());
                irParaTelaCapituloDetalhado.putExtra("lojaPatrocinadora", capitulo.getLojaPatrocinadora());
                irParaTelaCapituloDetalhado.putExtra("cidadeCap", capitulo.getCidade());

                startActivity(irParaTelaCapituloDetalhado);

            }
        });
    }

    public class Task extends AsyncTask<String, String, Void> {

        private ProgressDialog progressDialog = new ProgressDialog(ListarCapitulo.this);


        InputStream is = null;
        String result = "";

        protected void onPreExecute() {
            Runnable progressRunnable = new Runnable() {
                @Override
                public void run() {
                    progressDialog.cancel();
                }
            };
            handler.postDelayed(progressRunnable, 8000);

            progressDialog.setCanceledOnTouchOutside(false);
            progressDialog.setMessage("Listando Itens...");
            progressDialog.show();
            progressDialog.setOnCancelListener(new DialogInterface.OnCancelListener() {

                @Override
                public void onCancel(DialogInterface dialog) {
                    ListarCapitulo.Task.this.cancel(true);
                }
            });

        };

        @Override
        protected Void doInBackground(String... params) {

            String url = ipServidor.getIpServidor()+"/listarCapitulos.php";

            HttpClient httpClient = new DefaultHttpClient();
            HttpPost httpPost = new HttpPost(url);

            ArrayList<NameValuePair> param = new ArrayList<NameValuePair>();

            try {
                httpPost.setEntity(new UrlEncodedFormEntity(param));

                HttpResponse httpResponse = httpClient.execute(httpPost);
                HttpEntity httpEntity = httpResponse.getEntity();

                // ler o conteudo
                is = httpEntity.getContent();

            } catch (Exception e) {
                Log.e("log_tag", "Erro ao conectar com o banco de dados " + e.toString());
                Toast.makeText(ListarCapitulo.this, "Tente novamente.", Toast.LENGTH_LONG).show();
            }

            try
            {
                BufferedReader br = new BufferedReader(new InputStreamReader(is));
                StringBuilder sb = new StringBuilder();
                String line = "";

                while((line = br.readLine()) != null){
                    sb.append(line+"\n");
                }
                is.close();
                result = sb.toString();

            }catch(Exception e){
                Log.e("log_tag", "Erro ao converter o resultado " + e.toString());
            }
            return null;
        }

        protected void onPostExecute(Void v){

            try {
                JSONArray Jarray = new JSONArray(result);

                for (int i = 0; i < Jarray.length(); i++) {
                    JSONObject jsonObject = null;
                    jsonObject = Jarray.getJSONObject(i);

                    // output na tela
                    numeroCapitulo = jsonObject.getInt("numeroCapitulo");
                    nome = jsonObject.getString("nome");
                    cidade = jsonObject.getString("cidade");
                    endereco = jsonObject.getString("endereco");
                    lojaPatrocinadora = jsonObject.getString("lojaPatrocinadora");

                    Capitulo capitulo = new Capitulo();

                    capitulo.setNumeroCapitulo(numeroCapitulo);
                    capitulo.setNome(nome);
                    capitulo.setEndereco(endereco);
                    capitulo.setCidade(cidade);
                    capitulo.setLojaPatrocinadora(lojaPatrocinadora);

                    param.add(capitulo);

                    ArrayAdapter<Capitulo> ad = new ArrayAdapter<Capitulo>(ListarCapitulo.this, android.R.layout.simple_list_item_1, param);
                    listView.setAdapter(ad);
                }

                this.progressDialog.dismiss();

            } catch (Exception e) {
                Toast.makeText(getBaseContext(), "Falha ao carregar, por favor tente novamente mais tarde", Toast.LENGTH_LONG).show();
                Log.e("log_tag", "Error parsing data "+e.toString());
                this.progressDialog.dismiss();
                finish();
            }
        }
    }

}
