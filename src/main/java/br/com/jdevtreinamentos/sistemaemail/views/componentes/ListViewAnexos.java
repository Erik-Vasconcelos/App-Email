package br.com.jdevtreinamentos.sistemaemail.views.componentes;

import java.io.File;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import javafx.scene.layout.FlowPane;

public class ListViewAnexos extends FlowPane {

	private List<ComponentAnexo> listaComponenteAnexo = new ArrayList<>();

	public ListViewAnexos() {
		this.setVgap(5);
		this.setHgap(5);
		this.setPrefWidth(900);
		this.setMaxWidth(USE_COMPUTED_SIZE);
	}

	public List<ComponentAnexo> getItens() {
		return listaComponenteAnexo;
	}
	
	public void addAnexo(File anexo) {
		if (anexo != null) {
			if (!containsAnexo(anexo)) {
				ComponentAnexo viewAnexo = new ComponentAnexo(anexo);
				definirAcaoBotao(viewAnexo);
				this.listaComponenteAnexo.add(viewAnexo);

				this.getChildren().clear();
				this.getChildren().addAll(listaComponenteAnexo);

			}
		}
	}

	private void definirAcaoBotao(ComponentAnexo view) {
		String anexoRemover = view.getAnexo().getName();

		view.setActionBtnRemover(e -> {
			Iterator<ComponentAnexo> it = listaComponenteAnexo.iterator();
			while (it.hasNext()) {
				ComponentAnexo viewComponent = it.next();
				if (viewComponent.getAnexo().getName().equals(anexoRemover)) {
					listaComponenteAnexo.remove(viewComponent);
					this.getChildren().remove(viewComponent);

					return;
				}
			}
		});
	}

	private boolean containsAnexo(File file) {
		return listaComponenteAnexo.stream().anyMatch(ca -> ca.getAnexo().getName().equals(file.getName()));
	}

	public void clear() {
		this.getChildren().removeAll(listaComponenteAnexo);
		listaComponenteAnexo = new ArrayList<>();
	}

}
