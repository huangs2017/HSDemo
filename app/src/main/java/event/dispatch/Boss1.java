package event.dispatch;

public class Boss1 extends Manager {

    public Boss1(String name) {
        super(name);
    }

    @Override
    public void requestDispatch(Request request) {
        if (request.getRequestNumber() <= 2) {
            System.out.println("Boss1已经审批");
        } else {
            if (superior != null) {
                superior.requestDispatch(request);
            }
        }
    }


}
