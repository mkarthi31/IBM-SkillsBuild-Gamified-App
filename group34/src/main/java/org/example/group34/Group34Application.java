package org.example.group34;

import org.example.group34.model.Course;
import org.example.group34.repo.CourseRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;

@SpringBootApplication
public class  Group34Application implements CommandLineRunner {

	public static void main(String[] args) {SpringApplication.run(Group34Application.class, args);}

	@Autowired private CourseRepository courseRepository;
	@Override
	public void run(String... args) throws Exception {

		/*
		Course course1 = new Course("Web Development Basics","In this course, you’ll learn about basic computer functions and programming languages. You’ll get an overview of web development, including the scope of the two sides of web development: front-end and back-end. And you’ll learn about the difference between the internet " +
				"and the World Wide Web.","Web Development","https://skills.yourlearning.ibm.com/activity/PLAN-8749C02A78EC");

		Course course2 = new Course("Introduction to HTML and CSS","In this course, you will learn about the basic " +
				"structure of an HTML document and common elements to build a web page. You’ll explore HTML attributes and " +
				"understand the importance of organizing information. In addition, you will gain insight into CSS and explore " +
				"the CSS box model.","Web Development","https://skills.yourlearning.ibm.com/activity/MDL-263");

		Course course3 = new Course("Artificial Intelligence Fundamentals"," You’ll explore AI’s history, " +
				"then see how it can change the world. Along the way, you’ll deep dive into ways that AI makes " +
				"predictions, understands language and images, and learns using circuits inspired by " +
				"the human brain. ","Artificial Intelligence","https://skills.yourlearning.ibm.com/activity/PLAN-7913EE1DB030");

		Course course4 = new Course("Data Science Fundamentals", "Gain an understanding of data science concepts, including data analysis, visualization, and machine learning techniques.",
				"Data Science", "https://skillsbuild.org/students/course-catalog/data-science");


		Course course5 = new Course("Cybersecurity Basics", "Learn the fundamentals of cybersecurity, including threat analysis, risk management, and the implementation of security measures.",
				"Cybersecurity",
				"https://skills.yourlearning.ibm.com/activity/ILB-DNRPWDGQGMMY7GGD");

		Course course6 = new Course("Cloud Computing Essentials", "Explore the basics of cloud computing, including service models, deployment strategies, and the benefits of cloud solutions.",
				"Cloud Computing",
				"https://skills.yourlearning.ibm.com/activity/PLAN-2EC3A305F2C3");

		Course course8 = new Course("Blockchain Fundamentals", "Understand the core concepts of blockchain technology, its applications, and how it is transforming various industries.",
				"Blockchain", "https://skills.yourlearning.ibm.com/activity/ILB-NKQGKQQPRDJD4D7Q");

		Course course9 = new Course("Design Thinking for Innovation", "Learn the design thinking process to drive innovation and solve complex problems creatively.",
				"Design Thinking", "https://skills.yourlearning.ibm.com/activity/URL-BECFAD8E7F61");

		Course course10 = new Course("Introduction to Internet of Things (IoT)", "Explore the IoT ecosystem, including sensors, connectivity, data processing, and real-world applications.",
				"Internet of Things", "https://skills.yourlearning.ibm.com/channel/CNL_LCB_1567184468548");

		Course course11 = new Course("Python Programming for Beginners", "Start coding with Python, covering basic syntax, data structures, and writing simple programs.",
				"Programming", "https://skills.yourlearning.ibm.com/activity/URL-806B8D9B07FD");

		Course course12 = new Course("Agile and Scrum Overview", "Understand Agile methodologies and the Scrum framework to enhance project management and team collaboration.",
				"Project Management", "https://skills.yourlearning.ibm.com/activity/URL-VFQTSQCHLSK");

		Course course13 = new Course("Introduction to DevOps", "Learn about the DevOps culture, practices, and tools that integrate development and operations for faster software delivery.",
				"DevOps", "https://skills.yourlearning.ibm.com/activity/ISG-DL0060450G");

		Course course14 = new Course("Mobile App Development Basics", "Gain insights into mobile app development, including design principles, development frameworks, and deployment strategies.",
				"Mobile Development", "https://skills.yourlearning.ibm.com/activity/SN-COURSE-V1:IBMDEVELOPERSKILLSNETWORK+SW0101EN+V1");

		Course course15 = new Course("Big Data Analytics", "Explore techniques and tools for analyzing large datasets to uncover patterns, trends, and insights.",
				"Data Analytics", "https://skills.yourlearning.ibm.com/activity/PLAN-BC0FAEE8E439");

		Course course16 = new Course("Introduction to Quantum Computing", "Delve into the basics of quantum computing, including quantum bits, entanglement, and potential applications.",
				"Quantum Computing", "https://skills.yourlearning.ibm.com/channel/CNL_LCB_1583254994368");

		Course course17 = new Course("Ethical Hacking Fundamentals", "Learn the principles of ethical hacking, including penetration testing, vulnerability assessment, and security protocols.",
				"Cybersecurity", "https://skills.yourlearning.ibm.com/activity/URL-MUMJN-FH8TY");

		Course course19 = new Course("Project Management Essentials", "Understand key project management concepts, methodologies, and tools to effectively manage projects.",
				"Project Management", "https://skills.yourlearning.ibm.com/activity/ILB-DNRVRVRGKDNQPQ9X");


		Course course21 = new Course("Natural Language Processing Basics",
				"Explore the fundamentals of Natural Language Processing (NLP), including text processing, sentiment analysis, and chatbot development.",
				"Artificial Intelligence",
				"https://skills.yourlearning.ibm.com/activity/MDL-214");


		Course course22 = new Course("Introduction to Quantum Computing",
				"Dive into the world of quantum computing and understand quantum bits, superposition, and how quantum algorithms differ from classical computing.",
				"Quantum Computing",
				"https://skills.yourlearning.ibm.com/channel/CNL_LCB_1583254994368");


		Course course23 = new Course("Human-Centered Cybersecurity",
				"Learn how cybersecurity strategies can be designed with a human-centered approach, ensuring better security awareness and risk mitigation.",
				"Cybersecurity",
				"https://skills.yourlearning.ibm.com/activity/URL-BECFAD8E7F61");


		Course course24 = new Course("AI for Business Decision-Making",
				"Understand how AI is used to optimize business operations, improve decision-making, and drive innovation across industries.",
				"Artificial Intelligence",
				"https://skills.yourlearning.ibm.com/activity/PLAN-CD23ACF07EC2");


		Course course25 = new Course("Introduction to Edge Computing",
				"Explore the principles of edge computing and how it enables faster data processing and analytics closer to the data source.",
				"Cloud Computing",
				"https://skills.yourlearning.ibm.com/activity/ISG-DL52824G");

		courseRepository.save(course1);
		courseRepository.save(course2);
		courseRepository.save(course3);
		courseRepository.save(course4);
		courseRepository.save(course5);
		courseRepository.save(course6);
		courseRepository.save(course8);
		courseRepository.save(course9);
		courseRepository.save(course10);
		courseRepository.save(course11);
		courseRepository.save(course12);
		courseRepository.save(course13);
		courseRepository.save(course14);
		courseRepository.save(course15);
		courseRepository.save(course16);
		courseRepository.save(course17);
		courseRepository.save(course19);
		courseRepository.save(course25);
		courseRepository.save(course24);
		courseRepository.save(course23);
		courseRepository.save(course22);
		courseRepository.save(course21);
		*/

	}

}
