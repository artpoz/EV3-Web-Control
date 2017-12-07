
public class Command {
	private String httpMethod;
	private String move;
	private Integer speed;
	public String getMove() {
		return move;
	}
	public void setMove(String move) {
		this.move = move;
	}
	public Integer getSpeed() {
		return speed;
	}
	public void setSpeed(Integer speed) {
		this.speed = speed;
	}
	public String getHttpMethod() {
		return httpMethod;
	}
	public void setHttpMethod(String httpMethod) {
		this.httpMethod = httpMethod;
	}
	
	Command() {
		this.move = "";
		this.speed = 0;
		this.httpMethod = "";
	}
}
