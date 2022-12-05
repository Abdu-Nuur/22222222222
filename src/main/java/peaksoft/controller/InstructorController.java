package peaksoft.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import peaksoft.entity.Instructor;
import peaksoft.service.CourseService;
import peaksoft.service.InstructorService;

@Controller
@RequestMapping
public class InstructorController {


    private final InstructorService instructorService;

    private final CourseService courseService;

    @Autowired
    public InstructorController(InstructorService instructorService, CourseService courseService) {
        this.instructorService = instructorService;
        this.courseService = courseService;
    }

    @GetMapping("/allInstructors/{companyId}")
    private String getAllInstructors(@PathVariable("companyId")Long id, Model model) {
        model.addAttribute("instructors",instructorService.getAllInstructorsByCompanyId(id));
        model.addAttribute("companyId",id);
//        model.addAttribute("instructors",courseService.getAllCoursesById(id));
        return "instructors/mainInstructorPage";
    }


    @GetMapping("{companyId}/newInstructor")
    private String newInstructor(@PathVariable("companyId")Long id, Model model) {
        model.addAttribute("newInstructor",new Instructor());
        model.addAttribute("companyId",id);
        return "instructors/newInstructor";
    }

    @PostMapping("{companyId}/saveInstructor")
    private String saveInstructor(@PathVariable("companyId")Long id,
                                  @ModelAttribute("newInstructor")Instructor instructor) {
        instructorService.saveInstructorByCompanyId(id,instructor);
        return "redirect:/allInstructors/ " + id;
    }

    @GetMapping("/getInstructor/{id}")
    private String getInstructorById(@PathVariable("id")Long id,Model model) {
        model.addAttribute("id",instructorService.getInstructorById(id));
        return "instructors/mainInstructorPage";
    }

    @GetMapping("/editInstructor/{instructorId}")
    private String updateInstructor(@PathVariable("instructorId")Long instructorId,Model model) {
        Instructor instructor = instructorService.getInstructorById(instructorId);
        model.addAttribute("instructor",instructor);
        model.addAttribute("companyId",instructor.getCompany().getId());
        return "instructors/updateInstructor";
    }

    @PatchMapping("/{companyId}/{instructorId}/updateInstructor")
    private String  saveUpdate(@PathVariable("companyId")Long companyId,
                               @PathVariable("instructorId")Long id,
                               @ModelAttribute("instructor")Instructor instructor) {
        instructorService.updateInstructor(id,instructor);
        return "redirect:/allInstructors/ "+ companyId;
    }

    @DeleteMapping("/{id}/{instructorId}/delete")
    private String deleteInstructor(@PathVariable("id")Long id,@PathVariable("instructorId")Long instructorId) {
        instructorService.deleteInstructorById(instructorId);
        return "redirect:/allInstructors/ " + id;
    }


}
