package theegg.me.xk;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

@Controller
public class MainController {

	private final ApplyService applyService;

	@Autowired
	public MainController(ApplyService applyService) {
		this.applyService = applyService;
	}

	@RequestMapping({ "/", "" })
	String home(Model model) {
		Collection<Apply> currentList = applyService.getCurrentList();
		Collection<Apply> historyList = applyService.getHistoryList();
		System.out.println(currentList.size());

		model.addAttribute("currentList", currentList);
		model.addAttribute("historyList", historyList);

		return "index";
	}

	@RequestMapping(value = "/apply", method = RequestMethod.POST)
	String apply(@Valid Apply apply, BindingResult result) {

		if (result.hasErrors()) {
			System.out.println(result.getAllErrors());
			return "index";
		}

		applyService.apply(apply);

		return "redirect:/";
	}

	@RequestMapping(value = "/{id}/cancel")
	String cancel(@PathVariable("id") int id) {
		applyService.cancel(id);
		return "redirect:/";
	}

	@RequestMapping(value = "/{id}/redo")
	String redo(@PathVariable("id") int id) {

		applyService.redo(id);
		return "redirect:/";
	}

}
