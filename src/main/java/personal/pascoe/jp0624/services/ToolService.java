package personal.pascoe.jp0624.services;

import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import personal.pascoe.jp0624.models.Tool;
import personal.pascoe.jp0624.repositories.ToolRepository;

import java.util.List;
import java.util.NoSuchElementException;
import java.util.Optional;

@Service
@AllArgsConstructor
public class ToolService {

    @Autowired
    private ToolRepository toolRepository;

    public List<Tool> findAll() {
        return toolRepository.findAll();
    }

    public Optional<Tool> findById(Long id) {
        return toolRepository.findById(id);
    }

    public Tool findByToolCode(String toolCode) {
        return toolRepository.findByToolCode(toolCode).orElseThrow(NoSuchElementException::new);
    }

    public Tool save(Tool tool) {
        return toolRepository.save(tool);
    }

    public void deleteById(Long id) {
        toolRepository.deleteById(id);
    }
}