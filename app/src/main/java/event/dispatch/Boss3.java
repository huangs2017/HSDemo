package event.dispatch;

public class Boss3 extends Manager {

    public Boss3(String name) {
        super(name);
    }

    @Override
    public void requestDispatch(Request request) {
        if (request.getRequestNumber() <= 100) {
            System.out.println("Boss3已经审批");
        } else {
            System.out.println("这个我很忙，需要讨论一下");
        }
    }
}
