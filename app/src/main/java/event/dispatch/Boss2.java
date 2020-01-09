package event.dispatch;

public class Boss2 extends Manager {

    public Boss2(String name) {
        super(name);
    }

    @Override
    public void requestDispatch(Request request) {
        if (request.getRequestNumber() <= 10) {
            System.out.println("Boss2已经审批");
        } else {
            if (superior != null) {
                superior.requestDispatch(request);
            }
        }
    }
}
