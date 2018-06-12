<?php defined('BASEPATH') OR exit('No direct script access allowed');

require(APPPATH.'libraries/REST_Controller.php');
use Restserver\Libraries\REST_Controller;

class Sampleapi extends REST_Controller {

  function __construct(){
    parent::__construct();
    $this->load->model('Testerinfo');
  }

  function testerinfo_get(){
    // $id = $this->get('id');
    //$url  = $_SERVER["PHP_SELF"];
    $path = explode("/", $_SERVER["PHP_SELF"]);
    $id = end($path);
    $content['logs'] = $this->Testerinfo->get_last_ten_entries();
    //$id = end($this->uri->segment_array());
    //$id = $this->uri->segment(3);
    //print_r($content);
    //die();
    // $student = array(
    //   1=>array('first'=>'John', 'last_name'=>'Doe'),
    //   2=>array('first'=>'Jane', 'last_name'=>'Doe')
    // );

    if(isset($content['logs'][$id])){
      $this->response(array('status'=>'success', 'message'=>$content['logs'][$id]));
    }else{
      $this->response(array('status'=>'failure', 'message'=>'Unable to retrieve tester  '), 404);
    }

  }

  function testerinfo_all_get(){
    $content['testers'] = $this->Testerinfo->get_last_ten_entries();
    $this->response(array('status'=>'success', 'message'=>$content['testers']));
  }

  function hostname_post(){
    //print_r($this->put());
    //die();

    $this->Testerinfo->insert_entry();
    $this->response(array('status'=>'success'));
    //print_r("hello");
    //die();
  }

}
