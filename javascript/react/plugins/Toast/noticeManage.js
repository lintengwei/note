import React, { Component } from 'react';
import ReactDOM from 'react-dom';
import Notice from './notice';
import styles from './transition.module.scss';
import { CSSTransition, TransitionGroup } from 'react-transition-group';

const id = '__plugin_root_toast';
const activeNotices = [];
let lastTimeId = undefined;

export default class NoticeManage extends Component {
  render() {
    return (
      <TransitionGroup component={null}>
        {activeNotices.map(ele => {
          return (
            <CSSTransition
              key={ele.id}
              appear
              timeout={600}
              classNames={{
                appear: styles['fade-appear'],
                appearActive: styles['fade-appear-active'],
                enter: styles['fade-enter'],
                enterActive: styles['fade-enter-active'],
                exit: styles['exit-active'],
                exitActive: styles['fade-exit-active']
              }}
            >
              <Notice message={ele.message} type={ele.type} />
            </CSSTransition>
          );
        })}
      </TransitionGroup>
    );
  }
}

NoticeManage.push = function(obj) {
  this.clear();
  activeNotices.push(obj);
  this.pop(obj);
  ReactDOM.render(<NoticeManage />, document.getElementById(id));
};

NoticeManage.pop = function(obj) {
  lastTimeId = setTimeout(() => {
    let i = activeNotices.indexOf(obj);
    activeNotices.splice(i, 1);
    ReactDOM.render(<NoticeManage />, document.getElementById(id));
    lastTimeId = undefined;
  }, obj.duration);
};

NoticeManage.clear = function() {
  if (lastTimeId) {
    clearTimeout(lastTimeId);
    activeNotices.pop();
  }
};
