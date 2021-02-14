package com.southsystem.desafio.back.votos.service;

import com.southsystem.desafio.back.votos.entities.Associado;
import com.southsystem.desafio.back.votos.exception.MensagemException;
import com.southsystem.desafio.back.votos.repository.AssociadoRepository;
import org.json.JSONException;
import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;

@Service
public class AssociadoService {

    AssociadoRepository associadoRepository;

    @Autowired
    public AssociadoService(AssociadoRepository associadoRepository) {
        this.associadoRepository = associadoRepository;
    }

    public Associado salvar(Associado associado) throws MensagemException {
        try {
            this.validarAssosiado(associado);
            return associadoRepository.save(associado);
        } catch (IOException | JSONException e) {
            throw new MensagemException(e.getMessage());
        }
    }

    public Associado buscarCpf(String cpf) {
        return associadoRepository.buscarCpf(cpf);
    }

    public boolean validarCpf(String cpf) throws IOException, JSONException {
        boolean resposta = true;

        URL url = new URL(" https://user-info.herokuapp.com/users/" + cpf);
        HttpURLConnection conn = (HttpURLConnection) url.openConnection();
        conn.setRequestMethod("GET");
        conn.setRequestProperty("Accept", "application/json");

        if (conn.getResponseCode() != 200) {
            throw new RuntimeException("CPF Inválido!");
        }

        InputStreamReader in = new InputStreamReader(conn.getInputStream());
        BufferedReader br = new BufferedReader(in);
        String output;

        while ((output = br.readLine()) != null) {
            JSONObject resp = new JSONObject(output);
            String status = resp.getString("status");

            if (!status.equals("ABLE_TO_VOTE")) {
                resposta = false;
            }
        }

        conn.disconnect();

        return resposta;
    }

    private void validarAssosiado(Associado associado) throws MensagemException, IOException, JSONException {
        if (!associado.camposObrigatorios()){
            throw new MensagemException("Nome e CPF são obrigatorios!");
        }

        if (!this.validarCpf(associado.getCpf())) {
            throw new MensagemException("CPF não esta autorizado a executar está ação!");
        }

        if (associadoRepository.buscarCpf(associado.getCpf()) != null){
            throw new MensagemException("CPF já cadastrado!");
        }
    }
}
