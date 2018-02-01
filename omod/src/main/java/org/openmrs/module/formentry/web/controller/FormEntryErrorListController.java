package org.openmrs.module.formentry.web.controller;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Vector;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.openmrs.api.context.Context;
import org.openmrs.module.formentry.FormEntryError;
import org.openmrs.module.formentry.FormEntryService;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.ui.ModelMap;

@Controller
@RequestMapping(value="module/formentry/formEntryError.list")
public class FormEntryErrorListController  {

	/** Logger for this class and subclasses */
	protected final Log log = LogFactory.getLog(getClass());
//    @Autowired FormEntryService formEntryService;
	/**
	 *
	 */
	protected Object formBackingObject(HttpServletRequest request) throws ServletException {

		//default empty Object
		List<FormEntryError> errorList = new Vector<FormEntryError>();

		// not used

		return errorList;
	}

	/**
	 *
	 */
	@RequestMapping(method=RequestMethod.GET)
	public String referenceData(ModelMap modelAndView) throws Exception {
		//default empty Objects
		Integer errorSize = 0;

		//only fill the objects if the user has authenticated properly
		if (Context.isAuthenticated()) {
			FormEntryService formEntryService = Context.getService(FormEntryService.class);
			errorSize = formEntryService.getFormEntryErrorSize();
		}

		Map<String, Object> map = new HashMap<String, Object>();

//		map.put("errorSize", errorSize);
		modelAndView.put("errorSize", errorSize);
		return "module/formentry/formEntryErrorList";
	}

}