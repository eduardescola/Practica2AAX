package aar;

import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

public class ChatDao {

	Logger log = Logger.getLogger(ChatDao.class.getName());

	final DatabaseService d = new DatabaseService();

	public int addChat(String name, int employee1, int employee2) {
		try {
			Chat chat = new Chat(name, employee1, employee2);
			d.insertChat(chat);
		} catch (Exception ex) {
			log.log(Level.SEVERE, null, ex);
		}
		return 1;
	}

	public Chat getChat(Integer id) {
		return d.readChat(id);
	}

	public boolean deleteChat(Integer id) {
		return d.deleteChat(id);
	}

	public List<Chat> searchChat(String key, String value) {
		try {
			return d.searchChat(key, value);
		} catch (Exception ex) {
			log.log(Level.INFO, null, ex);
		}
		return null;
	}

	public List<Chat> getAllChats() {
		try {
			return d.findAllChats();
		} catch (Exception ex) {
			log.log(Level.SEVERE, null, ex);
		}
		return null;
	}

}