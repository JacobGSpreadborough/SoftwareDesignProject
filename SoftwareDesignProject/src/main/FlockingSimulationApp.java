package main;

public class FlockingSimulationApp {

	private SimulationEngine engine;

	public FlockingSimulationApp() {

		engine = new SimulationEngine();
	}

	private void start() {
		engine.start(10);
	}

	public static void main(String[] args) {

		FlockingSimulationApp simulation = new FlockingSimulationApp();
		simulation.start();
	}

}
