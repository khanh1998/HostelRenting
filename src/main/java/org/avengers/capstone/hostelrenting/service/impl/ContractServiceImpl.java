package org.avengers.capstone.hostelrenting.service.impl;

import com.aspose.pdf.HtmlLoadOptions;
import org.avengers.capstone.hostelrenting.dto.contract.ContractDTOShort;
import org.avengers.capstone.hostelrenting.exception.EntityNotFoundException;
import org.avengers.capstone.hostelrenting.model.*;
import org.avengers.capstone.hostelrenting.repository.ContractRepository;
import org.avengers.capstone.hostelrenting.service.*;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.mail.Message;
import javax.mail.PasswordAuthentication;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;
import java.io.File;
import java.util.List;
import java.util.Optional;
import java.util.Properties;

@Service
public class ContractServiceImpl implements ContractService {
    private ContractRepository contractRepository;
    private ModelMapper modelMapper;
    private RenterService renterService;
    private VendorService vendorService;
    private HostelRoomService roomService;
    private DealService dealService;
    private BookingService bookingService;

    @Autowired
    public void setDealService(DealService dealService) {
        this.dealService = dealService;
    }

    @Autowired
    public void setBookingService(BookingService bookingService) {
        this.bookingService = bookingService;
    }

    @Autowired
    public void setRoomService(HostelRoomService roomService) {
        this.roomService = roomService;
    }

    @Autowired
    public void setVendorService(VendorService vendorService) {
        this.vendorService = vendorService;
    }

    @Autowired
    public void setRenterService(RenterService renterService) {
        this.renterService = renterService;
    }

    @Autowired
    public void setModelMapper(ModelMapper modelMapper) {
        this.modelMapper = modelMapper;
    }

    @Autowired
    public void setContractRepository(ContractRepository contractRepository) {
        this.contractRepository = contractRepository;
    }


    @Override
    public void checkActive(Integer id) {
        Optional<Contract> model = contractRepository.findById(id);
        if (model.isEmpty())
            throw new EntityNotFoundException(Contract.class, "id", id.toString());
    }

    @Override
    public Contract findById(Integer id) {
        checkActive(id);

        return contractRepository.getOne(id);
    }

    @Override
    public Contract create(ContractDTOShort reqDTO) {
        Vendor exVendor = vendorService.findById(reqDTO.getVendorId());
        Renter exRenter = renterService.findById(reqDTO.getVendorId());
        Room exRoom = roomService.findById(reqDTO.getRoomId());

        Contract reqModel = modelMapper.map(reqDTO, Contract.class);

        // Update deal status if exist (contract created means deal is done)
        // Update booking status if exist (contract created means booking is done)
        Integer dealId = reqDTO.getDealId();
        Integer bookingId = reqDTO.getBookingId();
        if (dealId != null){
            dealService.checkActive(dealId);
            dealService.changeStatus(dealId, Deal.STATUS.DONE);
        }
        if (bookingId != null){
            bookingService.checkActive(bookingId);
            bookingService.changeStatus(bookingId, Booking.STATUS.DONE);
        }

        reqModel.setStatus(Contract.STATUS.WORKING);
        reqModel.setVendor(exVendor);
        reqModel.setRenter(exRenter);
        reqModel.setRoom(exRoom);
        reqModel.setCreatedAt(System.currentTimeMillis());

        return contractRepository.save(reqModel);
    }

    @Override
    public Contract update(ContractDTOShort reqDTO) {
        return null;
    }

    /**
     * Get list of contracts by given renter id
     *
     * @param renterId given renter id to get contract
     * @return list of contract models
     */
    @Override
    public List<Contract> findByRenterId(Long renterId) {
        renterService.checkExist(renterId);

        return renterService.findById(renterId).getContracts();
    }

    /**
     * Get list of contracts by given vendor id
     *
     * @param vendorId given vendor id to get contract
     * @return list of contract models
     */
    @Override
    public List<Contract> findByVendorId(Long vendorId) {
        vendorService.checkExist(vendorId);

        return vendorService.findById(vendorId).getContracts();
    }

    @Override
    public void createPDF() {
        // Create HTML load options
        HtmlLoadOptions htmloptions = new HtmlLoadOptions();
        // Load HTML file
        com.aspose.pdf.Document doc = new com.aspose.pdf.Document("HTML-Document.html", htmloptions);
        // Convert HTML file to PDF
        doc.save("HTML-to-PDF.pdf");
    }

    @Override
    public void sendMailWithEmbed(String receivedMail) {
//        String to = "thanhnv.se@gmail.com";

        // Sender's email ID needs to be mentioned
        String from = "winsupersentai@gmail.com";
        final String username = "winsupersentai@gmail.com";
        final String password = "Thanh748159263";

        String host = "smtp.gmail.com";

        Properties props = new Properties();
        props.put("mail.smtp.auth", "true");
        props.put("mail.smtp.starttls.enable", "true");
        props.put("mail.smtp.host", host);
        props.put("mail.smtp.port", "465");
        props.put("mail.smtp.debug", "true");
        props.put("mail.smtp.socketFactory.port", "465");
        props.put("mail.smtp.socketFactory.class", "javax.net.ssl.SSLSocketFactory");
        props.put("mail.smtp.socketFactory.fallback", "false");

        // Get the Session object.
        Session session = Session.getInstance(props,
                new javax.mail.Authenticator() {
                    protected PasswordAuthentication getPasswordAuthentication() {
                        return new PasswordAuthentication(username, password);
                    }
                });

        try {
            Message message = new MimeMessage(session);
            message.setFrom(new InternetAddress(from));
            message.setRecipients(Message.RecipientType.TO,
                    InternetAddress.parse(receivedMail));
            message.setSubject("Hợp đồng thuê nhà");
            File input = new File("D:\\\\capstone\\\\TDHostelGGGAPICrawler\\\\src\\\\main\\\\java\\\\util\\\\contract.html");
            Document doc = Jsoup.parse(input, "UTF-8", "");
            String html = doc.toString();
            message.setContent(html,"text/html; charset=UTF-8");
            // Send message
            Transport.send(message);

            System.out.println("Sent message successfully....");

        } catch (Exception e) {
            e.printStackTrace();
            throw new RuntimeException(e);
        }
    }

    private void setUpdatedTime(Contract exModel){
        exModel.setUpdatedAt(System.currentTimeMillis());
    }
}
