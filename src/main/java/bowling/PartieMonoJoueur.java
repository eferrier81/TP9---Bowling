package bowling;

import java.util.ArrayList;
import java.util.List;

public class PartieMonoJoueur {
	private static final int MAX_TOURS = 10;
	private final List<Tour> tours = new ArrayList<>();
	private int indexTourActuel = 0;

	public PartieMonoJoueur() {
		for (int i = 0; i < MAX_TOURS; i++) {
			tours.add(new Tour());
		}
	}

	public boolean enregistreLancer(int quillesTombees) {
		if (estTerminee()) {
			throw new IllegalStateException("La partie est terminée, plus de lancer possible.");
		}

		Tour tourCourant = tours.get(indexTourActuel);
		tourCourant.enregistrerLancer(quillesTombees);

		if (tourCourant.estStrike() || tourCourant.estTermine()) {
			indexTourActuel++;
			if (indexTourActuel == MAX_TOURS && tourCourant.estStrike()) {
				tours.add(new Tour(true)); // Tour bonus pour strike
				tours.add(new Tour(true)); // Deuxième tour bonus pour strike
			} else if (indexTourActuel == MAX_TOURS && tourCourant.estSpare()) {
				tours.add(new Tour(true)); // Tour bonus pour spare
			}
		}
		return !tourCourant.estTermine();
	}

	public int score() {
		int score = 0;
		for (int i = 0; i < Math.min(MAX_TOURS, tours.size()); i++) {
			score += tours.get(i).calculerScore(i, tours);
		}
		return score;
	}

	public boolean estTerminee() {
		return indexTourActuel >= tours.size();
	}

	public int numeroTourCourant() {
		return estTerminee() ? 0 : indexTourActuel + 1;
	}

	public int numeroProchainLancer() {
		if (estTerminee()) {
			return 0;
		}
		Tour tourCourant = tours.get(indexTourActuel);
		return (tourCourant.getQuillesPremierLancer() == -1) ? 1 : 2;
	}
}
