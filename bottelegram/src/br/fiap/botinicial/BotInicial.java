package br.fiap.botinicial;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.pengrad.telegrambot.TelegramBot;
import com.pengrad.telegrambot.model.Update;
import com.pengrad.telegrambot.request.GetUpdates;
import com.pengrad.telegrambot.request.SendMessage;
import com.pengrad.telegrambot.response.GetUpdatesResponse;

public class BotInicial {

	/**
	 * 
	 * pergunta.toLowerCase(), o tratamento das perguntas do Telegram s�o sempre em maiuscula.
	 * 
	 */
	
	public String buscarPergunta(String pergunta) {
		try {
			String resposta = MontandoPerguntasERespostas().get(pergunta.toLowerCase());
			if (resposta == null) {
				return "N�o entendi...";
			}
			return resposta;
		} catch (NullPointerException ex) {
			return "N�o entendi...";
		}
	}
	
/**
 * m�todo principal
 */
	public int lerMensagem (int count) {
		
		//cria��o do objeto bot com as informa��es de acesso
		TelegramBot bot = new TelegramBot("854043531:AAFN5ZkFvaryyWmxp2hdQPjHguT-jN2wikc");
		
		//objeto respons�vel por receber as mensagens
		GetUpdatesResponse updateResponse;
		
		//executa comando no Telegram para obter as mensagens pendentes a partir de um off-set (limite inicial)
		updateResponse = bot.execute(new GetUpdates().limit(100).offset(count));
		
		List<Update> updates = updateResponse.updates();
		
		//lista de mensagens
		if (updates.size() != 0) {
			Update update = updates.get(updates.size() - 1);
			
			String resposta = buscarPergunta(update.message().text());
			
			bot.execute(new SendMessage(update.message().chat().id(), resposta));
			
			return update.updateId();
		}
		return count;
	}

	/**
	 * As perguntas nesta lista Map, devem ser cadastradas em minuscula.
	 * No m�todo buscarPergunta que vem at� este m�todo pegar informa��es foi usado o comando
	 * .toLowerCase(), que ao trasformar o texto em maiusculo n�o vai diferenciar se foi digitado
	 * em maiusculo ou minusculo no Telegram.
	 * 
	 */
	
	public Map<String, String> MontandoPerguntasERespostas() {
		Map<String, String> opcoes = new HashMap<>();
		opcoes.put("ol�", "Oi");
		opcoes.put("tchau", "Tchau at� mais...");
		return opcoes;
	}
}
