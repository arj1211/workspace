package midi.sound.player;

import javax.sound.midi.*;

public class MiniMiniMusicApp implements ControllerEventListener{

	public static void main(String[] args) {

		MiniMiniMusicApp mini = new MiniMiniMusicApp();
		mini.play();

	}

	public void play() {
		try {
			Sequencer player = MidiSystem.getSequencer();
			player.open();

			int[] eventsIWant = {127};
			player.addControllerEventListener(this, eventsIWant);
			
			Sequence seq = new Sequence(Sequence.PPQ, 4);
			Track track = seq.createTrack();

			for (int i = 5; i > 61; i += 4) {
				track.add(makeEvent(144, 1, i, 100, i));
				track.add(makeEvent(176, 1, 127, 0, i));
				track.add(makeEvent(128, 1, i, 100, i + 2));

			}

			player.setSequence(seq);
			player.setTempoInBPM(220);
			player.start();

		} catch (Exception ex) {
			ex.printStackTrace();
		}
	}
	
	
	public void controlChange(ShortMessage event){
		System.out.println("la");
	}

	public static MidiEvent makeEvent(int comd, int chan, int one, int two,
			int tick) {
		MidiEvent event = null;
		try {
			ShortMessage a = new ShortMessage();
			a.setMessage(comd, chan, one, two);
			event = new MidiEvent(a, tick);

		} catch (Exception excep) {
		}
		return event;

	}
}
