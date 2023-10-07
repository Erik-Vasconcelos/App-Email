package br.com.jdevtreinamentos.sistemaemail.views.componentes;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import javafx.scene.layout.FlowPane;

public class ListViewDestinatarios extends FlowPane {

	private List<ComponentEmailDestinatario> listaComponenteEmail = new ArrayList<>();

	public ListViewDestinatarios() {
		this.setVgap(5);
		this.setHgap(5);
		this.setPrefWidth(900);
		this.setMaxWidth(USE_COMPUTED_SIZE);
	}

	public List<ComponentEmailDestinatario> getItens() {
		return listaComponenteEmail;
	}
	
	public void addDestinatario(String email) {
		if (email != null) {
			if (!containsDestinatario(email)) {
				ComponentEmailDestinatario view = new ComponentEmailDestinatario(email);
				definirAcaoBotao(view);
				this.listaComponenteEmail.add(view);

				this.getChildren().clear();
				this.getChildren().addAll(listaComponenteEmail);

			}
		}
	}

	private void definirAcaoBotao(ComponentEmailDestinatario view) {
		String emailRemover = view.getEmail();

		view.setActionBtnRemover(e -> {
			Iterator<ComponentEmailDestinatario> it = listaComponenteEmail.iterator();
			while (it.hasNext()) {
				ComponentEmailDestinatario viewComponent = it.next();
				if (viewComponent.getEmail().equals(emailRemover)) {
					listaComponenteEmail.remove(viewComponent);
					this.getChildren().remove(viewComponent);

					return;
				}
			}
		});
	}

	private boolean containsDestinatario(String email) {
		return listaComponenteEmail.stream().anyMatch(cw -> cw.getEmail().equals(email));
	}

	public void clear() {
		this.getChildren().removeAll(listaComponenteEmail);
		listaComponenteEmail = new ArrayList<>();
	}

}
