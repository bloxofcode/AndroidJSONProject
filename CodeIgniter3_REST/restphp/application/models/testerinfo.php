<?php defined('BASEPATH') OR exit('No direct script access allowed');

class Testerinfo extends CI_Model{

  public $host_name;

  public function __construct(){
    parent::__construct();
  }

  public function get_last_ten_entries(){
    $query = $this->db->get('tbl_tester', 10);
    return $query->result();
  }



  public function insert_entry(){
    $this->host_name    = $_POST['hostname']; // please read the below note
    // $this->content  = $_POST['content'];
    // $this->date     = time();
    //print_r($this);
    //die();

    $this->db->insert('tbl_tester', $this);
  }




}
