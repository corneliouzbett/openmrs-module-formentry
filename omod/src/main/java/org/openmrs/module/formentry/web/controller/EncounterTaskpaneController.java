/**
 * The contents of this file are subject to the OpenMRS Public License
 * Version 1.0 (the "License"); you may not use this file except in
 * compliance with the License. You may obtain a copy of the License at
 * http://license.openmrs.org
 *
 * Software distributed under the License is distributed on an "AS IS"
 * basis, WITHOUT WARRANTY OF ANY KIND, either express or implied. See the
 * License for the specific language governing rights and limitations
 * under the License.
 *
 * Copyright (C) OpenMRS, LLC.  All Rights Reserved.
 */
package org.openmrs.module.formentry.web.controller;

import java.util.Collections;
import java.util.List;
import java.util.Vector;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.directwebremoting.guice.RequestParameters;
import org.openmrs.Encounter;
import org.openmrs.EncounterType;
import org.openmrs.Patient;
import org.openmrs.api.APIAuthenticationException;
import org.openmrs.api.APIException;
import org.openmrs.api.EncounterService;
import org.openmrs.api.context.Context;
import org.openmrs.module.formentry.FormEntryConstants;
import org.springframework.web.bind.ServletRequestUtils;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.beans.factory.annotation.Autowired;

/**
 * Provides the backing controller for the encounter.htm taskpane page
 * 
 * This controller expects to be given the patientId and potentially an
 * encounter type to restrict to
 */

@Controller
@RequestMapping("formTaskpane.htm")
public class EncounterTaskpaneController {
	
    /** Logger for this class and subclasses */
    protected final Log log = LogFactory.getLog(getClass());

	@RequestMapping(method = RequestMethod.GET)
	public Object getTaskPanes(@RequestParam(value = "patientId") String patientId,
							@RequestParam(value = "encounterTypeId") int[] encounterTypeId){



		if (!Context.hasPrivilege(FormEntryConstants.PRIV_FORM_ENTRY))
			throw new APIAuthenticationException("You must have FormEntry privileges to continue");
		Patient patient = Context.getPatientService().getPatient(Integer.parseInt(patientId));
		if (patient == null)
			throw new APIException("A valid patientId is required for this page. " + patientId + " not found");

// find the relevant encounter types
		int[] encounterTypeIds = encounterTypeId;
		List<EncounterType> encounterTypes = new Vector<EncounterType>();
		for (int encTypeId : encounterTypeIds)
			encounterTypes.add(new EncounterType(encTypeId));

		// fetch the encounters for this patient
		EncounterService es = Context.getEncounterService();
		List<Encounter> encounters = es.getEncounters(patient, null, null, null, null, encounterTypes, false);

		if (encounters == null)
			return Collections.emptyList();
		else
			return encounters;
	}

//    /**
//     * @see org.springframework.web.servlet.mvc.Controller
//     */
//    protected Object formBackingObject(HttpServletRequest request) throws ServletException {
//
//    	if (!Context.hasPrivilege(formEntryConstants.PRIV_FORM_ENTRY))
//    		throw new APIAuthenticationException("You must have FormEntry privileges to continue");
//
//    	// find the relevant patient
//    	Integer patientId = ServletRequestUtils.getRequiredIntParameter(request, "patientId");
//    	Patient patient = Context.getPatientService().getPatient(patientId);
//    	if (patient == null)
//    		throw new APIException("A valid patientId is required for this page. " + patientId + " not found");
//
//    	// find the relevant encounter types
//    	int[] encounterTypeIds = ServletRequestUtils.getIntParameters(request, "encounterTypeId");
//		List<EncounterType> encounterTypes = new Vector<EncounterType>();
//		for (int encTypeId : encounterTypeIds)
//			encounterTypes.add(new EncounterType(encTypeId));
//
//    	// fetch the encounters for this patient
//		EncounterService es = Context.getEncounterService();
//		List<Encounter> encounters = es.getEncounters(patient, null, null, null, null, encounterTypes, false);
//
//		if (encounters == null)
//			return Collections.emptyList();
//		else
//			return encounters;
//
//    }
	
}