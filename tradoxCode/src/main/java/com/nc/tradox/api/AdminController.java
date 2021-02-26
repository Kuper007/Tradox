package com.nc.tradox.api;

import com.nc.tradox.model.*;
import com.nc.tradox.model.service.CountryView;
import com.nc.tradox.model.service.HaveDocumentView;
import com.nc.tradox.model.service.Response;
import com.nc.tradox.service.AdminService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpSession;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.logging.Logger;

@RequestMapping("api/v1/admin")
@RestController
public class AdminController {

    private final AdminService adminService;
    private final Logger LOGGER = Logger.getLogger(AdminController.class.getName());

    @Autowired
    public AdminController(AdminService adminService) {
        this.adminService = adminService;
    }

    @GetMapping("/getCountries")
    public Response getCounties(HttpSession httpSession) {
        Response response = new Response();
        if (adminAuthorized(httpSession)) {
            List<CountryView> countryList = adminService.getCountryList();
            response.setObject(countryList);
        } else {
            response.setError("permissionsError");
        }
        return response;
    }

    @PutMapping("/saveCountries")
    public Response saveCountries(@RequestBody List<CountryView> countryList, HttpSession httpSession) {
        Response response = new Response();
        if (adminAuthorized(httpSession)) {
            Map<String, String> result = new HashMap<>();
            for (CountryView countryView : countryList) {
                boolean updateResult = adminService.saveCountry(countryView);
                if (!updateResult) {
                    result.put(countryView.getShortName(), "false");
                }
            }
            response.setObject(result);
        } else {
            response.setError("permissionsError");
        }
        return response;
    }

    @PostMapping("/addCountry")
    public Response addCountry(@RequestBody CountryView countryView, HttpSession httpSession) {
        Response response = new Response();
        if (adminAuthorized(httpSession)) {
            boolean addResult = adminService.addCountry(countryView);
            response.setObject(addResult);
        } else {
            response.setError("permissionsError");
        }
        return response;
    }

    @DeleteMapping("/deleteCountries")
    public Response deleteCountries(@RequestBody List<CountryView> countryList, HttpSession httpSession) {
        Response response = new Response();
        if (adminAuthorized(httpSession)) {
            Map<String, String> result = new HashMap<>();
            for (CountryView countryView : countryList) {
                boolean deleteResult = adminService.deleteCountry(countryView);
                if (!deleteResult) {
                    result.put(countryView.getShortName(), "false");
                }
            }
            response.setObject(result);
        } else {
            response.setError("permissionsError");
        }
        return response;
    }

    @GetMapping("/getUsers")
    public Response getUsers(HttpSession httpSession) {
        Response response = new Response();
        if (adminAuthorized(httpSession)) {
            List<User> userList = adminService.getUserList();
            response.setObject(userList);
        } else {
            response.setError("permissionsError");
        }
        return response;
    }

    @PutMapping("/saveUsers")
    public Response saveUsers(@RequestBody List<User> userList, HttpSession httpSession) {
        Response response = new Response();
        if (adminAuthorized(httpSession)) {
            Map<Integer, String> result = new HashMap<>();
            for (User user : userList) {
                boolean updateResult = adminService.saveUser(user);
                if (!updateResult) {
                    result.put(user.getUserId(), "false");
                }
            }
            response.setObject(result);
        } else {
            response.setError("permissionsError");
        }
        return response;
    }

    @DeleteMapping("/deleteUsers")
    public Response deleteUsers(@RequestBody List<User> userList, HttpSession httpSession) {
        Response response = new Response();
        if (adminAuthorized(httpSession)) {
            Map<Integer, String> result = new HashMap<>();
            for (User user : userList) {
                boolean deleteResult = adminService.deleteUser(user);
                if (!deleteResult) {
                    result.put(user.getUserId(), "false");
                }
            }
            response.setObject(result);
        } else {
            response.setError("permissionsError");
        }
        return response;
    }

    @GetMapping("/getDocuments")
    public Response getDocuments(HttpSession httpSession) {
        Response response = new Response();
        if (adminAuthorized(httpSession)) {
            List<Document> documentList = adminService.getDocumentList();
            response.setObject(documentList);
        } else {
            response.setError("permissionsError");
        }
        return response;
    }

    @PutMapping("/saveDocuments")
    public Response saveDocuments(@RequestBody List<Document> documentList, HttpSession httpSession) {
        Response response = new Response();
        if (adminAuthorized(httpSession)) {
            Map<Integer, String> result = new HashMap<>();
            for (Document document : documentList) {
                boolean updateResult = adminService.saveDocument(document);
                if (!updateResult) {
                    result.put(document.getDocumentId(), "false");
                }
            }
            response.setObject(result);
        } else {
            response.setError("permissionsError");
        }
        return response;
    }

    @PostMapping("/addDocument")
    public Response addDocument(@RequestBody Document document, HttpSession httpSession) {
        Response response = new Response();
        if (adminAuthorized(httpSession)) {
            boolean addResult = adminService.addDocument(document);
            response.setObject(addResult);
        } else {
            response.setError("permissionsError");
        }
        return response;
    }

    @DeleteMapping("/deleteDocuments")
    public Response deleteDocuments(@RequestBody List<Document> documentList, HttpSession httpSession) {
        Response response = new Response();
        if (adminAuthorized(httpSession)) {
            Map<Integer, String> result = new HashMap<>();
            for (Document document : documentList) {
                boolean deleteResult = adminService.deleteDocument(document);
                if (!deleteResult) {
                    result.put(document.getDocumentId(), "false");
                }
            }
            response.setObject(result);
        } else {
            response.setError("permissionsError");
        }
        return response;
    }

    @GetMapping("/getConsulates")
    public Response getConsulates(HttpSession httpSession) {
        Response response = new Response();
        if (adminAuthorized(httpSession)) {
            List<Consulate> consulateList = adminService.getConsulateList();
            response.setObject(consulateList);
        } else {
            response.setError("permissionsError");
        }
        return response;
    }

    @PutMapping("/saveConsulates")
    public Response saveConsulates(@RequestBody List<Consulate> consulateList, HttpSession httpSession) {
        Response response = new Response();
        if (adminAuthorized(httpSession)) {
            Map<Integer, String> result = new HashMap<>();
            for (Consulate consulate : consulateList) {
                boolean updateResult = adminService.saveConsulate(consulate);
                if (!updateResult) {
                    result.put(consulate.getConsulateId(), "false");
                }
            }
            response.setObject(result);
        } else {
            response.setError("permissionsError");
        }
        return response;
    }

    @PostMapping("/addConsulate")
    public Response addConsulate(@RequestBody Consulate consulate, HttpSession httpSession) {
        Response response = new Response();
        if (adminAuthorized(httpSession)) {
            boolean addResult = adminService.addConsulate(consulate);
            response.setObject(addResult);
        } else {
            response.setError("permissionsError");
        }
        return response;
    }

    @DeleteMapping("/deleteConsulates")
    public Response deleteConsulates(@RequestBody List<Consulate> consulateList, HttpSession httpSession) {
        Response response = new Response();
        if (adminAuthorized(httpSession)) {
            Map<Integer, String> result = new HashMap<>();
            for (Consulate consulate : consulateList) {
                boolean deleteResult = adminService.deleteConsulate(consulate);
                if (!deleteResult) {
                    result.put(consulate.getConsulateId(), "false");
                }
            }
            response.setObject(result);
        } else {
            response.setError("permissionsError");
        }
        return response;
    }

    @GetMapping("/getCountryDocuments")
    public Response getCountryDocuments(HttpSession httpSession) {
        Response response = new Response();
        if (adminAuthorized(httpSession)) {
            List<HaveDocumentView> countryDocumentList = adminService.getCountryDocumentList();
            response.setObject(countryDocumentList);
        } else {
            response.setError("permissionsError");
        }
        return response;
    }

    @PutMapping("/saveCountryDocuments")
    public Response saveCountryDocuments(@RequestBody List<HaveDocumentView> haveDocumentViewList, HttpSession httpSession) {
        Response response = new Response();
        if (adminAuthorized(httpSession)) {
            Map<Integer, String> result = new HashMap<>();
            for (HaveDocumentView haveDocumentView : haveDocumentViewList) {
                boolean updateResult = adminService.saveCountryDocument(haveDocumentView);
                if (!updateResult) {
                    result.put(haveDocumentView.getId(), "false");
                }
            }
            response.setObject(result);
        } else {
            response.setError("permissionsError");
        }
        return response;
    }

    @PostMapping("/addCountryDocument")
    public Response addCountryDocument(@RequestBody HaveDocumentView haveDocumentView, HttpSession httpSession) {
        Response response = new Response();
        if (adminAuthorized(httpSession)) {
            boolean addResult = adminService.addCountryDocument(haveDocumentView);
            response.setObject(addResult);
        } else {
            response.setError("permissionsError");
        }
        return response;
    }

    @DeleteMapping("/deleteCountryDocuments")
    public Response deleteCountryDocuments(@RequestBody List<HaveDocumentView> haveDocumentViewList, HttpSession httpSession) {
        Response response = new Response();
        if (adminAuthorized(httpSession)) {
            Map<Integer, String> result = new HashMap<>();
            for (HaveDocumentView haveDocumentView : haveDocumentViewList) {
                boolean deleteResult = adminService.deleteCountryDocument(haveDocumentView);
                if (!deleteResult) {
                    result.put(haveDocumentView.getId(), "false");
                }
            }
            response.setObject(result);
        } else {
            response.setError("permissionsError");
        }
        return response;
    }

    @GetMapping("/getMedicines")
    public Response getMedicines(HttpSession httpSession) {
        Response response = new Response();
        if (adminAuthorized(httpSession)) {
            List<Medicine> medicineList = adminService.getMedicineList();
            response.setObject(medicineList);
        } else {
            response.setError("permissionsError");
        }
        return response;
    }

    @PutMapping("/saveMedicines")
    public Response saveMedicines(@RequestBody List<Medicine> medicineList, HttpSession httpSession) {
        Response response = new Response();
        if (adminAuthorized(httpSession)) {
            Map<Integer, String> result = new HashMap<>();
            for (Medicine medicine : medicineList) {
                boolean updateResult = adminService.saveMedicine(medicine);
                if (!updateResult) {
                    result.put(medicine.getMedicineId(), "false");
                }
            }
            response.setObject(result);
        } else {
            response.setError("permissionsError");
        }
        return response;
    }

    @PostMapping("/addMedicine")
    public Response addMedicine(@RequestBody Medicine medicine, HttpSession httpSession) {
        Response response = new Response();
        if (adminAuthorized(httpSession)) {
            boolean addResult = adminService.addMedicine(medicine);
            response.setObject(addResult);
        } else {
            response.setError("permissionsError");
        }
        return response;
    }

    @DeleteMapping("/deleteMedicines")
    public Response deleteMedicines(@RequestBody List<Medicine> medicineList, HttpSession httpSession) {
        Response response = new Response();
        if (adminAuthorized(httpSession)) {
            Map<Integer, String> result = new HashMap<>();
            for (Medicine medicine : medicineList) {
                boolean deleteResult = adminService.deleteMedicine(medicine);
                if (!deleteResult) {
                    result.put(medicine.getMedicineId(), "false");
                }
            }
            response.setObject(result);
        } else {
            response.setError("permissionsError");
        }
        return response;
    }

    @GetMapping("/getStatuses")
    public Response getStatuses(HttpSession httpSession) {
        Response response = new Response();
        if (adminAuthorized(httpSession)) {
            List<Status> statusList = adminService.getStatusList();
            response.setObject(statusList);
        } else {
            response.setError("permissionsError");
        }
        return response;
    }

    @PutMapping("/saveStatuses")
    public Response saveStatuses(@RequestBody List<Status> statusList, HttpSession httpSession) {
        Response response = new Response();
        if (adminAuthorized(httpSession)) {
            Map<Integer, String> result = new HashMap<>();
            for (Status status : statusList) {
                boolean updateResult = adminService.saveStatus(status);
                if (!updateResult) {
                    result.put(status.getStatusId(), "false");
                }
            }
            response.setObject(result);
        } else {
            response.setError("permissionsError");
        }
        return response;
    }

    @PostMapping("/addStatus")
    public Response addStatus(@RequestBody Status status, HttpSession httpSession) {
        Response response = new Response();
        if (adminAuthorized(httpSession)) {
            boolean addResult = adminService.addStatus(status);
            response.setObject(addResult);
        } else {
            response.setError("permissionsError");
        }
        return response;
    }

    @DeleteMapping("/deleteStatuses")
    public Response deleteStatuses(@RequestBody List<Status> statusList, HttpSession httpSession) {
        Response response = new Response();
        if (adminAuthorized(httpSession)) {
            Map<Integer, String> result = new HashMap<>();
            for (Status status : statusList) {
                boolean deleteResult = adminService.deleteStatus(status);
                if (!deleteResult) {
                    result.put(status.getStatusId(), "false");
                }
            }
            response.setObject(result);
        } else {
            response.setError("permissionsError");
        }
        return response;
    }

    public boolean adminAuthorized(HttpSession httpSession) {
        Integer userId = (Integer) httpSession.getAttribute("userId");
        if (isValidUser(userId)) {
            String userType = (String) httpSession.getAttribute("userType");
            return "admin".equals(userType);
        }
        return false;
    }

    private boolean isValidUser(Integer userId) {
        if (userId != null) {
            return userId >= 0;
        }
        return false;
    }

}