package kindlereport.web;

import java.util.Date;
import java.util.Map;
import java.util.Map.Entry;
import java.util.concurrent.ConcurrentHashMap;

import kindlereport.dao.KindleMapper;
import kindlereport.model.Kindle;
import kindlereport.model.WebSocketReceive;
import kindlereport.model.WebSocketTransmission;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.socket.CloseStatus;
import org.springframework.web.socket.TextMessage;
import org.springframework.web.socket.WebSocketSession;
import org.springframework.web.socket.handler.TextWebSocketHandler;

import com.fasterxml.jackson.databind.ObjectMapper;

/**
 * エコーハンドラーです。
 */
@Component
public class EchoHandler extends TextWebSocketHandler {
	private static final Logger logger = LoggerFactory.getLogger(EchoHandler.class);
	@Autowired
	private KindleMapper kindleMapper;
	
    /**
     * セッションプールです。
     */
    private Map<String, WebSocketSession> sessionPool = new ConcurrentHashMap<>();
    /**
     * 接続が確立したセッションをプールします。
     * @param session セッション
     * @throws Exception 例外が発生した場合
     */
    @Override
    public void afterConnectionEstablished(WebSocketSession session) throws Exception {
        this.sessionPool.put(session.getId(), session);
    }
    /**
     * 切断された接続をプールから削除します。
     * @param session セッション
     * @param status ステータス
     * @throws Exception 例外が発生した場合
     */
    @Override
    public void afterConnectionClosed(
            WebSocketSession session,
            CloseStatus status) throws Exception {
        this.sessionPool.remove(session.getId());
    }
    /**
     * ハンドリングしたテキストメッセージをグローバルキャストします。
     * @param session セッション
     * @param message メッセージ
     */
    @Override
    public void handleTextMessage(WebSocketSession session, TextMessage message) throws Exception {
        for (Entry<String, WebSocketSession> entry : this.sessionPool.entrySet()) {

        	//受信したJSON文字列をwebSocketReceiveに変換
        	ObjectMapper mapper = new ObjectMapper();
        	WebSocketReceive webSocketReceive = mapper.readValue(message.getPayload(), WebSocketReceive.class);
        	//logger.info("Receive : {}", message.getPayload());
        	
        	//WebSocketTransmissionを作成
        	WebSocketTransmission webSocketTransmission = receiveToTransmission(webSocketReceive);
        	
        	//更新したWebSocketTransmissionをJSON文字列に変換
        	String json = mapper.writeValueAsString(webSocketTransmission);
        	TextMessage returnMessage = new TextMessage(json);
        	//logger.info("Transmission : {}", json);
        	
            entry.getValue().sendMessage(returnMessage);
        }
    }
    
    private WebSocketTransmission receiveToTransmission(WebSocketReceive webSocketReceive){
    	WebSocketTransmission webSocketTransmission = new WebSocketTransmission();
    	Map<String,String> kindle = kindleMapper.selectKindle(webSocketReceive.getAsin());
    	
    	webSocketTransmission.setAsin(webSocketReceive.getAsin());
    	webSocketTransmission.setImgUrl(kindle.get("largeImage"));
    	webSocketTransmission.setMessage(webSocketReceive.getMessage());
    	webSocketTransmission.setTitle(kindle.get("title"));
    	webSocketTransmission.setDateTime(getCurrentTime());
    	
    	return webSocketTransmission;
    }
    
    private Date getCurrentTime(){
    	Date date = new Date();
    	
    	return date;
    }
}
