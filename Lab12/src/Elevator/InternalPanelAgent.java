package Elevator;

import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.BlockingQueue;

public class InternalPanelAgent extends Thread{
    static class InternalCall{
        private final int toFloor;
        InternalCall(int toFloor){
            this.toFloor = toFloor;
        }
    }

    InternalPanelAgent(ElevatorCar elevatorCar){
        this.elevatorCar = elevatorCar;
    }

    BlockingQueue<InternalCall> input = new ArrayBlockingQueue<>(100);
    ElevatorCar elevatorCar;

    public void run(){
        for(;;){
            // odczytaj wezwanie z kolejki
            InternalPanelAgent.InternalCall ic = null;
            try {
                ic = input.take();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }

            //ignorujemy wezwanie na to pietro
            if(ic.toFloor==elevatorCar.getFloor()) continue;


            // w zależności od aktualnego piętra, na którym jest winda,
            // umieść przystanek w odpowiedniej tablicy ''EleveatorStops''

            if (ic.toFloor > elevatorCar.floor) {
                ElevatorStops.get().setLiftStopUp(ic.toFloor);
            } else {
                ElevatorStops.get().setLiftStopDown(ic.toFloor);
            }

        }
    }
}
