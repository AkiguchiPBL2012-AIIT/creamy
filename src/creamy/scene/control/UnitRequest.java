package creamy.scene.control;

public interface UnitRequest extends Requestable {
    public String getMethod();
    public String getPath();
}
