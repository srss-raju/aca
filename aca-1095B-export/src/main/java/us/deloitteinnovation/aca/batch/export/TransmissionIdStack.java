package us.deloitteinnovation.aca.batch.export;

import java.util.List;
import java.util.Stack;

/**
 * Created by bhchaganti on 5/16/2016.
 */
public class TransmissionIdStack {

    Stack<Integer> transmissionIdStack = new Stack();

    public TransmissionIdStack() {}

    public void buildStack(List<Integer> transmissionIds){

        for(Integer transmissionId : transmissionIds)
        transmissionIdStack.push(transmissionId);
    }

    public Integer popTransmissionId() {

        return transmissionIdStack.pop();
    }

    public Integer peekTransmissionId() {

        return transmissionIdStack.peek();
    }

    public boolean isStackEmpty(){
        return transmissionIdStack.isEmpty();
    }
}
