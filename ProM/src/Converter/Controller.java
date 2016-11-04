package Converter;

import BPMN.*;
import Petri.*;

/**
 * Created by Erdem on 04-Nov-16.
 */
public class Controller {
    public static Petri convertToPetri(BPMN inputBpmn){
        Event startEvent = getStartEvent(inputBpmn);
        return Converter.convert(startEvent);
    }

    private static Event getStartEvent(BPMN inputBpmn){
        return (Event) inputBpmn.getNodes().stream().filter(Controller::isStartEvent).findFirst().get();
    }
    private static boolean isStartEvent(Node n){
        if (n instanceof Event){
            Event e = (Event) n;
            return e.getType().equals("start");
        }
        return false;
    }
}
